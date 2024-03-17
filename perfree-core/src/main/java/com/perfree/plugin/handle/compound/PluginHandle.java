package com.perfree.plugin.handle.compound;


import cn.hutool.core.util.URLUtil;
import com.perfree.plugin.*;
import com.perfree.plugin.commons.PluginUtils;
import com.perfree.plugin.core.PluginClassLoader;
import jakarta.annotation.Resource;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Component
public class PluginHandle implements ApplicationContextAware {

    // 主程序 applicationContext
    ApplicationContext applicationContext;

    @Resource
    private PluginCompoundHandle pluginCompoundHandle;

    @Resource
    ConfigurableApplicationContext context;

    @Resource
    private PluginManagerHandle pluginManagerHandle;


    private static final Logger LOGGER = LoggerFactory.getLogger(PluginHandle.class);

    /**
     * 启动插件逻辑
     * @param pluginDir pluginDir
     * @return  PluginInfo
     * @throws Exception
     */
    public PluginInfo startPlugin(String pluginId) throws Exception {
        List<Class<?>> classList = new ArrayList<>();
        pluginManagerHandle.stopPlugin(pluginId);
        PluginWrapper pluginWrapper = pluginManagerHandle.getPlugin(pluginId);
        BasePlugin basePlugin = (BasePlugin) pluginManagerHandle.getPlugin(pluginId).getPlugin();
        String scanPackage = basePlugin.scanPackage();
        Set<String> classPackageName = scanClassPackageName(scanPackage, pluginWrapper);
        for (String packageName : classPackageName) {
            ClassLoader pluginClassLoader = pluginWrapper.getPluginClassLoader();
            Class<?> clazz = pluginClassLoader.loadClass(packageName);
            if (!BasePlugin.class.isAssignableFrom(clazz)) {
                classList.add(clazz);
            }
        }
        PluginInfo pluginInfo = new PluginInfo();
        pluginInfo.setPluginId(pluginWrapper.getPluginId());
        pluginInfo.setClassList(classList);
        pluginInfo.setPluginPath(pluginWrapper.getPluginPath().toString());
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.setParent(applicationContext);
        annotationConfigApplicationContext.setClassLoader(pluginWrapper.getPluginClassLoader());
        PluginApplicationContextHolder.addPluginApplicationContext(pluginInfo.getPluginId(), annotationConfigApplicationContext);
        pluginCompoundHandle.initialize();
        pluginCompoundHandle.registry(pluginInfo);
        PluginInfoHolder.addPluginInfo(pluginInfo.getPluginId(), pluginInfo);
        return pluginInfo;
    }

    /**
     * 停止插件
     * @param pluginId pluginId
     * @throws Exception
     */
    public void stopPlugin(String pluginId) throws Exception {
        PluginInfo pluginInfo = PluginInfoHolder.getPluginInfo(pluginId);
        LOGGER.info("plugin  ----->  plugin msg load complete: {}", pluginInfo);
        pluginCompoundHandle.initialize();
        pluginCompoundHandle.unRegistry(pluginInfo);
        // 移除插件专属AnnotationConfigApplicationContext
        PluginApplicationContextHolder.removePluginApplicationContext(pluginId);
        PluginInfoHolder.removePluginInfo(pluginId);
        LOGGER.info("plugin  ----->  stop success: {}", pluginInfo);
        pluginInfo.setPluginConfig(null);
        pluginInfo.setClassList(null);
        pluginManagerHandle.stopPlugin(pluginId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static Set<String> scanClassPackageName(String basePackage, PluginWrapper pluginWrapper) throws IOException {
        String pluginPath = pluginWrapper.getPluginPath().toString();
        Set<String> classPackageNames = new HashSet<>();
        try (JarFile jarFile = new JarFile(pluginPath)) {
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry entry = jarEntries.nextElement();
                String jarEntryName = entry.getName();
                if (jarEntryName.contains(".class") && jarEntryName.replaceAll("/", ".").startsWith(basePackage)) {
                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                    classPackageNames.add(className);
                }
            }
        }
        return classPackageNames;
    }
}
