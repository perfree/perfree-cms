package com.perfree.plugin.handle;

import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginClassLoaderHolder;
import com.perfree.plugin.PluginInfo;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

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
            String pluginPath = plugin.getPluginPath();
            String xmlLocationPattern = "mapper/*.xml";
            xmlLocationPattern = xmlLocationPattern.replaceAll("\\*\\*", "<>").replaceAll("\\*", "<>")
                    .replaceAll("\\.", "\\.").replaceAll("<>", ".*");

            File jarFile = new File(pluginPath);
            Enumeration<JarEntry> jarEntries = new JarFile(jarFile).entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry entry = jarEntries.nextElement();
                String jarEntryName = entry.getName();
                if (Pattern.matches(xmlLocationPattern, jarEntryName) && jarEntryName.endsWith(".xml")) {
                    URL url = new URL("jar:file:" + jarFile.getAbsolutePath() + "!/" + jarEntryName);
                    JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
                    InputStream in = jarConnection.getInputStream();
                    try {
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in,
                                configuration, url.getPath(), configuration.getSqlFragments());
                        xmlMapperBuilder.parse();
                        in.close();
                    } catch (Exception e) {
                        throw new Exception("Failed to parse mapping resource: '" + url.getPath() + "'", e);
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                        ErrorContext.instance().reset();
                        JarFile currJarFile = jarConnection.getJarFile();
                        currJarFile.close();
                    }
                }
            }
        } finally {
            Resources.setDefaultClassLoader(ClassUtils.getDefaultClassLoader());
        }
    }

    @Override
    public void unRegistry(PluginInfo plugin) throws Exception {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) PluginApplicationContextHolder.getApplicationContext(plugin.getPluginId()).getBean("sqlSessionFactory");
        Configuration configuration = sqlSessionFactory.getConfiguration();
        clearValues(configuration, "mappedStatements");
        clearValues(configuration, "caches");
        clearValues(configuration, "resultMaps");
        clearValues(configuration, "parameterMaps");
        clearValues(configuration, "keyGenerators");
        clearValues(configuration, "sqlFragments");
        Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
        loadedResourcesField.setAccessible(true);
        ((Set<?>) loadedResourcesField.get(configuration)).clear();
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