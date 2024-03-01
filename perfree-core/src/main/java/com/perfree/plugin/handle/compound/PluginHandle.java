package com.perfree.plugin.handle.compound;


import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.util.ClassLoaderUtil;
import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.plugin.PluginClassLoaderHolder;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.PluginInfoHolder;
import com.perfree.plugin.commons.PluginUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

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
        JarClassLoader jarClassLoader = ClassLoaderUtil.getJarClassLoader(pluginDir);
        PluginClassLoaderHolder.addPluginJarClassLoader(pluginInfo.getPluginId(), jarClassLoader);
        pluginInfo.setClassList(PluginUtils.getClassList(pluginDir,jarClassLoader));
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
        // 移除插件JarClassLoader
        PluginClassLoaderHolder.removePluginJarClassLoader(pluginId);
        PluginInfoHolder.removePluginInfo(pluginId);
        LOGGER.info("plugin  ----->  stop success: {}", pluginInfo);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
