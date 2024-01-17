package com.perfree.plugin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.util.ClassLoaderUtil;
import com.perfree.plugin.handle.compound.PluginHandle;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.shared.invoker.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Perfree
 * @description 插件管理类,提供插件安装/卸载/运行/停止等方法
 * @date 15:36 2023/9/28
 */
@Component
public class PluginDevManager implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginDevManager.class);

    // 主程序 applicationContext
    ApplicationContext applicationContext;

    private final PluginHandle pluginHandle;

    public PluginDevManager(PluginHandle pluginHandle) {
        this.pluginHandle = pluginHandle;
    }

    /**
     * 初始化所有插件
     * @author perfree
     * @date 2023-09-27 16:09:44
     */
    public void initPlugins() {
        List<File> jarFiles = getPlugins();
        if (null == jarFiles || jarFiles.isEmpty()) {
            return;
        }
        int id = 1;
        for (File pluginJarFile : jarFiles) {
            runPluginJar(pluginJarFile, id);
            id++;
        }
    }

    /**
     * 获取所有插件并编译
     * @return List<File>
     */
    private List<File> getPlugins() {
        String projectRootPath = System.getProperty("user.dir");
        // 打包插件
        LOGGER.info("开始编译打包插件...");
        File pluginsPom = new File(projectRootPath + "/perfree-plugins/pom.xml");
        String mavenHome = System.getenv("M2_HOME");
        if (StringUtils.isBlank(mavenHome)) {
            mavenHome = System.getenv("MAVEN_HOME");
        }
        if (StringUtils.isBlank(mavenHome)) {
            LOGGER.error("未查找到MAVEN_HOME环境变量,无法自动编译插件包,请先配置环境变量后再运行");
            return null;
        }
        System.setProperty("maven.home", mavenHome);
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pluginsPom);
        request.setGoals(Arrays.asList("clean", "package", "-DskipTests"));

        Invoker invoker = new DefaultInvoker();
        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            LOGGER.error("编译插件失败!未在项目根目录执行maven install或插件程序错误", e);
            return null;
        }

        LOGGER.info("插件编译完成");
        File pluginsDir = new File(projectRootPath + "/perfree-plugins");
        File[] files = pluginsDir.listFiles();
        if (null == files){
            return null;
        }
        List<File> jarList = new ArrayList<>();
        FileFilter jarFileFilter = pathname -> pathname.isFile() && pathname.getName().endsWith(".jar");
        for (File file : files) {
            if (file.isDirectory()) {
                List<File> jarFiles = FileUtil.loopFiles(file.getAbsoluteFile() + "/target", jarFileFilter);
                if (!jarFiles.isEmpty()) {
                    jarList.add(jarFiles.get(0));
                } else {
                    LOGGER.warn("编译插件[{}]完成,但未查找导编译后生成的jar文件,请排查插件是否有误!", file.getName());
                }
            }
        }
        return jarList;
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
