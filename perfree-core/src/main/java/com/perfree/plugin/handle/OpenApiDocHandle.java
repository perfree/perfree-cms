package com.perfree.plugin.handle;

import cn.hutool.core.util.ReflectUtil;
import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginInfo;
import io.swagger.v3.oas.models.OpenAPI;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.AbstractOpenApiResource;
import org.springdoc.core.configurer.SpringdocActuatorBeanFactoryConfigurer;
import org.springdoc.core.configurer.SpringdocBeanFactoryConfigurer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.service.OpenAPIService;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class OpenApiDocHandle implements BasePluginRegistryHandler{

    private final static Class<?>[] REGISTER_ANNO = {RestController.class};
    ApplicationContext applicationContext;

    private OpenAPIService openApiService;
    private List<Class<?>> restControllers;

    private SpringDocConfigProperties springDocConfigProperties;

    public OpenApiDocHandle(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public void initialize() throws Exception {
        openApiService = applicationContext.getBean(OpenAPIService.class);
        AbstractOpenApiResource openApiResource = applicationContext.getBean(AbstractOpenApiResource.class);
        Field additionalRestControllers = ReflectUtil.getField(openApiResource.getClass(), "ADDITIONAL_REST_CONTROLLERS");
        additionalRestControllers.setAccessible(true);
        restControllers = (List<Class<?>>) additionalRestControllers.get(null);
        springDocConfigProperties = applicationContext.getBean(SpringDocConfigProperties.class);
    }

    @Override
    public void registry(PluginInfo pluginInfo) throws Exception {
//        SpringDocConfigProperties.GroupConfig groupConfig = new SpringDocConfigProperties.GroupConfig();
//        List<String> packagesToScan = new ArrayList<>();
//        packagesToScan.add("com.exam");
//        groupConfig.setPackagesToScan(packagesToScan);
//
//        List<String> pathsToMatch = new ArrayList<>();
//        pathsToMatch.add("/**");
//        groupConfig.setPathsToMatch(pathsToMatch);
//        groupConfig.setDisplayName("测试");
//        groupConfig.setGroup("测试");
//        springDocConfigProperties.addGroupConfig(groupConfig);
        List<Class<?>> pluginClassList = pluginInfo.getClassList().stream().filter(item -> !item.isInterface()).toList();
        if(!pluginClassList.isEmpty()) {
            for (Class<?> aClass : pluginClassList) {
                Annotation[] annotations = aClass.getAnnotations();
                if (annotations.length > 0 && Collections.disjoint(Arrays.asList(annotations), Arrays.asList(REGISTER_ANNO))) {
                    restControllers.add(aClass);
                }
            }
        }
        OpenAPI cachedOpenAPI = openApiService.getCachedOpenAPI(Locale.getDefault());
        openApiService.setCachedOpenAPI(null, Locale.getDefault());
        openApiService.build(Locale.getDefault());
        /*Method setCachedOpenApiMethod = ReflectUtil.getMethod(openApiService.getClass(), "setCachedOpenAPI", OpenAPI.class, Locale.class);
        if(setCachedOpenApiMethod != null){
            setCachedOpenApiMethod.invoke(openApiService, null, null);
        }*/
    }

    @Override
    public void unRegistry(PluginInfo pluginInfo) throws Exception {
        List<Class<?>> pluginClassList = pluginInfo.getClassList().stream().filter(item -> !item.isInterface()).toList();
        if(!pluginClassList.isEmpty()) {
            for (Class<?> aClass : pluginClassList) {
                Annotation[] annotations = aClass.getAnnotations();
                if (annotations.length > 0 && Collections.disjoint(Arrays.asList(annotations), Arrays.asList(REGISTER_ANNO))) {
                    restControllers.remove(aClass);
                }
            }
        }
        openApiService.setCachedOpenAPI(null, Locale.getDefault());
        openApiService.build(Locale.getDefault());
        /*Method setCachedOpenApiMethod = ReflectUtil.getMethod(openApiService.getClass(), "setCachedOpenAPI", OpenAPI.class, Locale.class);
        if(setCachedOpenApiMethod != null){
            setCachedOpenApiMethod.invoke(openApiService, null, null);
        }*/
    }

}
