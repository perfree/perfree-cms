package com.perfree.plugin.handle;

import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.annotation.InterceptPath;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Override
    public void unRegistry(PluginInfo pluginInfo) throws Exception {

    }
}
