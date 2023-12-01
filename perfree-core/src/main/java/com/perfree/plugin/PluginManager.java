package com.perfree.plugin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.util.ClassLoaderUtil;
import com.perfree.plugin.handle.compound.PluginHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Perfree
 * @description 插件管理类,提供插件安装/卸载/运行/停止等方法
 * @date 15:36 2023/9/28
 */
@Component
public class PluginManager implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginManager.class);

    // 主程序 applicationContext
    ApplicationContext applicationContext;

    private final PluginHandle pluginHandle;

    public PluginManager(PluginHandle pluginHandle) {
        this.pluginHandle = pluginHandle;
    }

    /**
     * 初始化所有插件
     * @author perfree
     * @date 2023-09-27 16:09:44
     */
    public void initPlugins() {
        List<File> jarFiles = FileUtil.loopFiles("E:\\111", file -> file.getName().toLowerCase().endsWith(".jar"));
        int id = 1;
        for (File pluginJarFile : jarFiles) {
            runPluginJar(pluginJarFile, id);
            id++;
        }
    }

    /**
     * 运行插件
     * @author perfree
     * @date 2023-09-27 16:09:02
     * @param pluginJarFile 插件jar文件
     * @param pluginId 插件id
     */
    public void runPluginJar(File pluginJarFile, int pluginId) {
        try {
            LOGGER.info("plugin  ----->  load plugin jar msg, jarFile: {}", pluginJarFile);
            PluginInfo pluginInfo = new PluginInfo();
            pluginInfo.setPluginId(pluginId);

            // 加载插件JarClassLoader
            JarClassLoader jarClassLoader = ClassLoaderUtil.getJarClassLoader(pluginJarFile);
            PluginClassLoaderHolder.addPluginJarClassLoader(pluginId, jarClassLoader);
            pluginInfo.setClassList(getClassList(pluginJarFile,jarClassLoader));

            // 加载插件专属AnnotationConfigApplicationContext
            AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
            annotationConfigApplicationContext.setParent(applicationContext);
            PluginApplicationContextHolder.addPluginApplicationContext(pluginId, annotationConfigApplicationContext);

            LOGGER.info("plugin  ----->  plugin msg load complete: {}", pluginInfo);
            pluginHandle.initialize();
            pluginHandle.registry(pluginInfo);
            LOGGER.info("plugin  ----->  start success: {}", pluginInfo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("plugin  ----->  start error:{}", e.getMessage(), e);
        }
    }

    /**
     * 停止插件
     * @author perfree
     * @date 2023-09-27 16:09:24
     * @param pluginJarFile 插件jar文件
     * @param pluginId 插件id
     */
    public void stopPluginJar(File pluginJarFile, int pluginId) {
        try {
            PluginInfo pluginInfo = new PluginInfo();
            pluginInfo.setPluginId(pluginId);
            pluginInfo.setClassList(getClassList(pluginJarFile,PluginClassLoaderHolder.getJarClassLoader(pluginId)));

            LOGGER.info("plugin  ----->  plugin msg load complete: {}", pluginInfo);
            pluginHandle.initialize();
            pluginHandle.unRegistry(pluginInfo);
            // 移除插件专属AnnotationConfigApplicationContext
            PluginApplicationContextHolder.removePluginApplicationContext(pluginId);
            // 移除插件JarClassLoader
            PluginClassLoaderHolder.removePluginJarClassLoader(pluginId);
            LOGGER.info("plugin  ----->  stop success: {}", pluginInfo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("plugin  ----->  stop error:{}", e.getMessage(), e);
        }
    }

    /**
     * 获取插件jar中所有的class集合
     * @author perfree
     * @date 2023-09-27 16:09:53
     * @param pluginJarFile 插件jar文件
     * @param classLoader 插件classLoader
     * @return java.util.List<java.lang.Class < ?>>
     */
    private List<Class<?>> getClassList(File pluginJarFile, ClassLoader classLoader) {
        List<Class<?>> classList = new ArrayList<>();
        try (JarFile JarFile = new JarFile(pluginJarFile);) {
            Enumeration<JarEntry> entries = JarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                    String className = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                    Class<?> aClass = classLoader.loadClass(className);
                    classList.add(aClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("plugin -> initClassNameList error: {}", e.getMessage(), e);
        }
        return classList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
