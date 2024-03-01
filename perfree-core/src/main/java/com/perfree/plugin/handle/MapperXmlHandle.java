package com.perfree.plugin.handle;

import cn.hutool.core.io.FileUtil;
import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginClassLoaderHolder;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.commons.PluginUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.util.ClassUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        SqlSessionFactory bean = PluginApplicationContextHolder.getApplicationContext(plugin.getPluginId()).getBean(SqlSessionFactory.class);
        org.apache.ibatis.session.Configuration configuration = bean.getConfiguration();
        clearValues(configuration, "mappedStatements");
        clearValues(configuration, "caches");
        clearValues(configuration, "resultMaps");
        clearValues(configuration, "parameterMaps");
        clearValues(configuration, "keyGenerators");
        clearValues(configuration, "sqlFragments");
        Field loadedResourcesField = configuration.getClass().getSuperclass().getDeclaredField("loadedResources");
        loadedResourcesField.setAccessible(true);
        ((Set<?>) loadedResourcesField.get(configuration)).clear();
        System.out.println(111);
    }


    private void clearValues(Configuration configuration, String fieldName) throws Exception {

        Field field = configuration.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Map<?, ?> map = (Map<?, ?>) field.get(configuration);
        DefaultSqlSession.StrictMap<Object> newMap = new DefaultSqlSession.StrictMap<>();
        for (Object key : map.keySet()) {
            try {
                newMap.put((String) key, map.get(key));
            } catch (IllegalArgumentException ex) {
                newMap.put((String) key, ex.getMessage());
            }
        }
        field.set(configuration, newMap);
    }
}