package com.perfree.plugin.handle;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginClassLoaderHolder;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.annotation.InterceptPath;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * @author Perfree
 * @description Class处理
 * @date 15:35 2023/9/28
 */
public class ClassHandler implements BasePluginRegistryHandler{
    // 只要是带以下注解的,全部注册bean
    private final static Class<?>[] REGISTER_ANNO = {Bean.class, Configuration.class, Component.class, RestController.class,
            Controller.class, Mapper.class, Service.class, Repository.class, InterceptPath.class};

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void registry(PluginInfo pluginInfo) throws Exception {
        // mybatis-plus实现一系列框架自定义bean,必须先注册mapper接口,否则会报错
        registerMapper(pluginInfo);
        // 此处逻辑: 只要类里包含REGISTER_ANNO中任意一个注解,就注册进入Bean容器中
        List<Class<?>> pluginClassList = pluginInfo.getClassList().stream().filter(item -> !item.isInterface()).toList();
        if(!pluginClassList.isEmpty()) {
            List<Class<?>> registryClassList = new ArrayList<>();
            for (Class<?> aClass : pluginClassList) {
                Annotation[] annotations = aClass.getAnnotations();
                if (annotations.length > 0 && Collections.disjoint(Arrays.asList(annotations), Arrays.asList(REGISTER_ANNO))) {
                    registryClassList.add(aClass);
                }
            }
            if(!registryClassList.isEmpty()) {
                PluginApplicationContextHolder.getApplicationContext(pluginInfo.getPluginId()).register(registryClassList.toArray(new Class[0]));
                // 刷新容器
                PluginApplicationContextHolder.getApplicationContext(pluginInfo.getPluginId()).refresh();
            }
        }
    }


    /**
     * 注册mapper接口
     * @param pluginInfo pluginInfo
     */
    private void registerMapper(PluginInfo pluginInfo) {
        List<Class<?>> mapperClassList = getMapperList(pluginInfo);
        if (mapperClassList.isEmpty()) return;

        //注册mapper
        for (Class<?> mapperClass : mapperClassList) {
            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(mapperClass);
            definition.setBeanClass(MapperFactoryBean.class);
            definition.getPropertyValues().add("addToConfig", true);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

            PluginApplicationContextHolder.getApplicationContext(pluginInfo.getPluginId()).registerBeanDefinition(mapperClass.getName(), definition);
        }

    }

    /**
     * @description 获取所有Mapper接口
     * @author Perfree
     * @date 2021/11/13 8:31
     */
    private List<Class<?>> getMapperList(PluginInfo plugin){
        List<Class<?>> mapperClassList = new ArrayList<>();

        for (Class<?> aClass : plugin.getClassList()) {
            Mapper annotation = aClass.getAnnotation(Mapper.class);
            if (annotation != null) {
                mapperClassList.add(aClass);
            }
        }
        return mapperClassList;
    }

    @Override
    public void unRegistry(PluginInfo pluginInfo) throws Exception {
        // 取消注册mapper接口
        List<Class<?>> mapperClassList = getMapperList(pluginInfo);
        if (mapperClassList.isEmpty()) return;

        for (Class<?> mapperClass : mapperClassList) {
            PluginApplicationContextHolder.getApplicationContext(pluginInfo.getPluginId()).removeBeanDefinition(mapperClass.getName());
        }
    }
}
