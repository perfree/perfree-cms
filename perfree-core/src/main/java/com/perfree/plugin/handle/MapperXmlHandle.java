package com.perfree.plugin.handle;

import cn.hutool.core.io.FileUtil;
import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginClassLoaderHolder;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.commons.PluginUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.util.ClassUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description 自定义Mybatis Mapper注册
 * @author Perfree
 * @date 2021/11/9 14:24
 */
public class MapperXmlHandle implements BasePluginRegistryHandler {
    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void registry(PluginInfo plugin) throws Exception {
        //注册mapper.xml
        SqlSessionFactory bean = PluginApplicationContextHolder.getApplicationContext(plugin.getPluginId()).getBean(SqlSessionFactory.class);
        org.apache.ibatis.session.Configuration configuration = bean.getConfiguration();

        try {
            Resources.setDefaultClassLoader(PluginClassLoaderHolder.getJarClassLoader(plugin.getPluginId()));
            List<File> mapperXml = PluginUtils.getMapperXml(new File(plugin.getPluginPath()), plugin.getPluginConfig());
            for (File file : mapperXml) {
                BufferedInputStream inputStream = FileUtil.getInputStream(file);
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream,
                        configuration, file.getAbsolutePath(), configuration.getSqlFragments());
                xmlMapperBuilder.parse();
                inputStream.close();
            }
        } finally {
            Resources.setDefaultClassLoader(ClassUtils.getDefaultClassLoader());
        }
    }

    @Override
    public void unRegistry(PluginInfo plugin) throws Exception {
        List<String> mapperXml = PluginUtils.getMapperXmlPath(new File(plugin.getPluginPath()), plugin.getPluginConfig());
        SqlSessionFactory bean = PluginApplicationContextHolder.getApplicationContext(plugin.getPluginId()).getBean(SqlSessionFactory.class);
        org.apache.ibatis.session.Configuration configuration = bean.getConfiguration();
        clearValues(configuration, "mappedStatements", mapperXml);
        clearValues(configuration, "caches", mapperXml);
        clearValues(configuration, "resultMaps", mapperXml);
        clearValues(configuration, "parameterMaps", mapperXml);
        clearValues(configuration, "keyGenerators", mapperXml);
        clearValues(configuration, "sqlFragments", mapperXml);
        Field loadedResourcesField = configuration.getClass().getSuperclass().getDeclaredField("loadedResources");
        loadedResourcesField.setAccessible(true);
        ((Set<?>) loadedResourcesField.get(configuration)).clear();
    }


    private void clearValues(Configuration configuration, String fieldName,  List<String> mapperXml) throws Exception {
        Field field = configuration.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Map<?, ?> map = (Map<?, ?>) field.get(configuration);
        ConcurrentMap<Object, Object> newMap = new ConcurrentHashMap<>();
        for (Object key : map.keySet()) {
            try {
                MappedStatement o = (MappedStatement) map.get(key);
                if (!mapperXml.contains(o.getResource())) {
                    newMap.put(key, map.get(key));
                }
            } catch (IllegalArgumentException ex) {
                newMap.put(key, ex.getMessage());
            }
        }
        field.set(configuration, newMap);
    }
}