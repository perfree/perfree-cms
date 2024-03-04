package com.perfree.plugin.handle.compound;


import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.URLUtil;
import com.perfree.commons.utils.SpringBeanUtil;
import com.perfree.config.MybatisPlusConfig;
import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginClassLoaderHolder;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.PluginInfoHolder;
import com.perfree.plugin.commons.PluginUtils;
import com.perfree.plugin.core.PluginClassLoader;
import jakarta.annotation.Resource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class PluginHandle implements ApplicationContextAware {

    // 主程序 applicationContext
    ApplicationContext applicationContext;

    @Resource
    private PluginCompoundHandle pluginCompoundHandle;

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginHandle.class);

    /**
     * 启动插件逻辑
     * @param pluginDir pluginDir
     * @return  PluginInfo
     * @throws Exception
     */
    public PluginInfo startPlugin(File pluginDir) throws Exception {
        PluginInfo pluginInfo = new PluginInfo();
        // 加载插件配置文件
        pluginInfo.setPluginConfig(PluginUtils.getPluginConfig(pluginDir));
        pluginInfo.setPluginId(pluginInfo.getPluginConfig().getPlugin().getName());
        // 加载插件JarClassLoader
       // JarClassLoader jarClassLoader = ClassLoaderUtil.getJarClassLoader(pluginDir);
        PluginClassLoader pluginClassLoader = PluginClassLoaderHolder.getJarClassLoader(pluginInfo.getPluginId());
        if (null == pluginClassLoader) {
            pluginClassLoader = new PluginClassLoader(new URL[]{URLUtil.getURL(pluginDir)}, this.getClass().getClassLoader());
        }
        PluginClassLoaderHolder.addPluginJarClassLoader(pluginInfo.getPluginId(), pluginClassLoader);
        pluginInfo.setClassList(PluginUtils.getClassList(pluginDir,pluginClassLoader));
        pluginInfo.setPluginPath(pluginDir.getAbsolutePath());
        // 加载插件专属AnnotationConfigApplicationContext
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.setParent(applicationContext);
        PluginApplicationContextHolder.addPluginApplicationContext(pluginInfo.getPluginId(), annotationConfigApplicationContext);
        LOGGER.info("plugin  ----->  plugin msg load complete: {}", pluginInfo);
        pluginCompoundHandle.initialize();
        pluginCompoundHandle.registry(pluginInfo);
        PluginInfoHolder.addPluginInfo(pluginInfo.getPluginId(), pluginInfo);
        LOGGER.info("plugin  ----->  start success: {}", pluginInfo);
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
        // 移除插件JarClassLoader
        PluginClassLoaderHolder.removePluginJarClassLoader(pluginId);
        LOGGER.info("plugin  ----->  stop success: {}", pluginInfo);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
