package com.perfree.plugin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.perfree.plugin.pojo.PluginBaseConfig;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Perfree
 * @description 源码运行插件加载类
 * @date 15:36 2023/01/18
 */
@Component
public class PluginDevManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginDevManager.class);

    @Value("${perfree.plugin-dir}")
    private String pluginDir;

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
       // FileUtil.copy(new File("E:\\my-work\\java\\perfree-cms\\perfree-plugins\\perfree-plugins-exam\\target\\classes"), new File("E:\\my-work\\java\\1111"), true);
        //pluginManager.runPluginJar(new File("E:\\my-work\\java\\1111\\classes"), 1);
        List<String> plugins = getPluginClassPath();
        if (null == plugins || plugins.isEmpty()) {
            return;
        }
        for (String plugin : plugins) {
            File pluginDir = new File(plugin);
            if (!pluginDir.exists()) {
                LOGGER.error("{} not found", plugin);
                continue;
            }
            File file = new File(plugin + File.separator + "plugin.yaml");
            if (!file.exists()) {
                LOGGER.error("{} plugin.yaml not found", plugin);
                continue;
            }
            Yaml yaml = new Yaml();
            PluginBaseConfig load = yaml.loadAs(new FileReader(file).readString(), PluginBaseConfig.class);
            File[] files = pluginDir.listFiles();
            if (null == files || files.length == 0) {
                continue;
            }
            int id = 1;
            for (File pluginSource : files) {
                File copy = FileUtil.copy(pluginSource, new File("E:\\my-work\\java\\1111\\" + load.getPlugin().getName()), true);
                pluginManager.runPluginJar(copy, id);
                id++;
            }
        }
    }

    /**
     * 获取启用的插件
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


    private List<String> getPluginClassPath() {
        String projectRootPath = System.getProperty("user.dir");
        File pluginsPom = new File(projectRootPath + "/perfree-plugins/pom.xml");
        File pluginsDir = new File(projectRootPath + "/perfree-plugins");
        File[] files = pluginsDir.listFiles();
        if (null == files){
            return null;
        }
        List<String> pluginPaths =  new ArrayList<>();
        List<String> enablePlugins = getEnablePlugin(pluginsPom);
        if (null == enablePlugins || enablePlugins.isEmpty()) {
            return null;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                File pluginPom = new File(file.getAbsoluteFile() + "/pom.xml");
                String pluginArtifactId = getPluginArtifactId(pluginPom);
                if (enablePlugins.contains(pluginArtifactId)) {
                    pluginPaths.add(file.getAbsoluteFile() + "/target/classes");
                }
            }
        }
        return pluginPaths;
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
