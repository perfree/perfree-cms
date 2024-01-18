package com.perfree.plugin;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.shared.invoker.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Perfree
 * @description 源码运行插件加载类
 * @date 15:36 2023/01/18
 */
@Component
public class PluginDevManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginDevManager.class);

    private final PluginManager pluginManager;

    public PluginDevManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
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
            pluginManager.runPluginJar(pluginJarFile, id);
            id++;
        }
    }

    /**
     * 获取所有插件并编译
     * @return List<File>
     */
    private List<File> getPlugins() {
        try{
            String projectRootPath = System.getProperty("user.dir");
            // Package plugins
            LOGGER.info("Start compiling and packaging plugins...");
            String mavenHome = System.getenv("M2_HOME");
            if (StringUtils.isBlank(mavenHome)) {
                mavenHome = System.getenv("MAVEN_HOME");
            }
            if (StringUtils.isBlank(mavenHome)) {
                LOGGER.error("MAVEN_HOME environment variable not found, unable to compile plugin package automatically, please configure the environment variable before running");
                return null;
            }
            System.setProperty("maven.home", mavenHome);
            File pluginsPom = new File(projectRootPath + "/perfree-plugins/pom.xml");
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(pluginsPom);
            request.setGoals(Arrays.asList("clean", "package", "-DskipTests"));

            Invoker invoker = new DefaultInvoker();
            invoker.setOutputHandler(null);
            try {
                invoker.execute(request);
            } catch (MavenInvocationException e) {
                LOGGER.error("Failed to compile plugins! Either maven install was not executed at the project root or there is an error in the plugin program", e);
                return null;
            }

            LOGGER.info("Plugin compilation completed");
            return getPluginJar(projectRootPath, pluginsPom);
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取启用的插件
     * @param pluginsPom pluginsPom
     * @return List<String>
     */
    private List<String> getEnablePlugin(File pluginsPom){
        try (FileInputStream fileInputStream = new FileInputStream(pluginsPom)) {
            MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
            Model model = mavenXpp3Reader.read(fileInputStream);
            return model.getModules();
        } catch (Exception e) {
            LOGGER.error("Failed parse plugin pom.xml", e);
        }
        return null;
    }


    /**
     * 获取插件jar
     * @param projectRootPath projectRootPath
     * @param pluginsPom pluginsPom
     * @return List<File>
     */
    private List<File> getPluginJar(String projectRootPath, File pluginsPom) {
        File pluginsDir = new File(projectRootPath + "/perfree-plugins");
        File[] files = pluginsDir.listFiles();
        if (null == files){
            return null;
        }
        List<File> jarList = new ArrayList<>();
        FileFilter jarFileFilter = pathname -> pathname.isFile() && pathname.getName().endsWith(".jar");
        List<String> enablePlugins = getEnablePlugin(pluginsPom);
        if (null == enablePlugins || enablePlugins.isEmpty()) {
            return null;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                File pluginPom = new File(file.getAbsoluteFile() + "/pom.xml");
                String pluginArtifactId = getPluginArtifactId(pluginPom);
                if (enablePlugins.contains(pluginArtifactId)) {
                    List<File> jarFiles = FileUtil.loopFiles(file.getAbsoluteFile() + "/target", jarFileFilter);
                    if (!jarFiles.isEmpty()) {
                        jarList.add(jarFiles.get(0));
                    } else {
                        LOGGER.warn("Compilation of plugin [{}] completed, but failed to find the compiled jar file. Please check if the plugin is faulty!", file.getName());
                    }
                }
            }
        }
        return jarList;
    }

    /**
     * 获取插件ArtifactId
     * @param pomFilePath pomFilePath
     * @return String
     */
    private String getPluginArtifactId(File pomFilePath) {
        try (FileInputStream fileInputStream = new FileInputStream(pomFilePath)) {
            MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
            Model model = mavenXpp3Reader.read(fileInputStream);
            return model.getArtifactId();
        } catch (Exception e) {
            LOGGER.error("Failed parse plugin pom.xml", e);
        }
        return null;
    }
}
