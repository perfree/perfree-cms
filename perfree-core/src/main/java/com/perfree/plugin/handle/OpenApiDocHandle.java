package com.perfree.plugin.handle;

import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.commons.ReflectionUtils;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.api.AbstractOpenApiResource;
import org.springdoc.core.service.OpenAPIService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OpenApiDocHandle implements BasePluginRegistryHandler{

    private final static Class<?>[] REGISTER_ANNO = {RestController.class};
    ApplicationContext applicationContext;

    private OpenAPIService openApiService;
    private List<Class<?>> restControllers;
    public OpenApiDocHandle(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public void initialize() throws Exception {
        openApiService = applicationContext.getBean(OpenAPIService.class);
        AbstractOpenApiResource openApiResource = applicationContext.getBean(AbstractOpenApiResource.class);
        restControllers = (List<Class<?>>) ReflectionUtils.getField(null, openApiResource.getClass(),
                "ADDITIONAL_REST_CONTROLLERS", List.class);
    }

    @Override
    public void registry(PluginInfo pluginInfo) throws Exception {
        List<Class<?>> pluginClassList = pluginInfo.getClassList().stream().filter(item -> !item.isInterface()).toList();
        if(!pluginClassList.isEmpty()) {
            for (Class<?> aClass : pluginClassList) {
                Annotation[] annotations = aClass.getAnnotations();
                if (annotations.length > 0 && Collections.disjoint(Arrays.asList(annotations), Arrays.asList(REGISTER_ANNO))) {
                    restControllers.add(aClass);
                }
            }
        }
        //applicationContext.addRegistryInfo(CONTROLLER_API_CLASS, apiClass);
        Method setCachedOpenApiMethod =
                ReflectionUtils.findMethod(openApiService.getClass(), "setCachedOpenAPI", OpenAPI.class);
        if(setCachedOpenApiMethod != null){
            setCachedOpenApiMethod.invoke(openApiService, null);
        }
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
        //applicationContext.addRegistryInfo(CONTROLLER_API_CLASS, apiClass);
        Method setCachedOpenApiMethod =
                ReflectionUtils.findMethod(openApiService.getClass(), "setCachedOpenAPI", OpenAPI.class);
        if(setCachedOpenApiMethod != null){
            setCachedOpenApiMethod.invoke(openApiService, null);
        }
    }

}
