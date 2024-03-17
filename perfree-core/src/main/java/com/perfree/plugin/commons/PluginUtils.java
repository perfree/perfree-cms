package com.perfree.plugin.commons;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.perfree.plugin.pojo.PluginBaseConfig;
import com.perfree.plugin.pojo.PluginBasisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class PluginUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginUtils.class);

    /**
     * 获取插件配置信息
     * @param pluginFile pluginFile
     * @return PluginBaseConfig
     */
    public static PluginBaseConfig getPluginConfig(File pluginFile) {
        File file = new File(pluginFile.getAbsolutePath() + File.separator + "plugin.yaml");
        if (!file.exists()) {
            LOGGER.error("plugin.yaml not found");
            return null;
        }
        Yaml yaml = new Yaml();
        HashMap<String, Object> hashMap = yaml.loadAs(new FileReader(file).readString(), HashMap.class);
        PluginBaseConfig pluginBaseConfig = new PluginBaseConfig();
        PluginBasisConfig pluginBasisConfig = new PluginBasisConfig();
        HashMap<String, Object> plugin = (HashMap<String, Object>) hashMap.get("plugin");
        pluginBasisConfig.setName(plugin.get("name").toString());
        pluginBasisConfig.setMapperLocation(plugin.get("mapperLocation").toString());
        pluginBaseConfig.setPlugin(pluginBasisConfig);
        return pluginBaseConfig;
    }

    /**
     * 判断是否为更新插件
     * @param pluginConfig PluginBaseConfig
     * @param pluginBaseDir pluginBaseDir
     * @return Boolean
     */
    public static Boolean isUpdate(PluginBaseConfig pluginConfig,String pluginBaseDir) {
        File file = new File(pluginBaseDir + File.separator + pluginConfig.getPlugin().getName());
        return file.exists();
    }

    /**
     * 将临时插件文件拷贝至指定目录
     */
    public static File copyPluginTempToPlugin(File pluginTempDir, String pluginBaseDir, Boolean isDelTemp) {
        PluginBaseConfig pluginConfig = getPluginConfig(pluginTempDir);
        if (null == pluginConfig) {
            LOGGER.error("plugin.yaml parse fail");
            return null;
        }
        File[] files = pluginTempDir.listFiles();
        if (null == files) {
           return null;
        }
        File destDirFile = new File(pluginBaseDir + File.separator + pluginConfig.getPlugin().getName());
        if (!destDirFile.exists()) {
            FileUtil.mkdir(destDirFile);
        }
        for (File pluginSource : files) {
            FileUtil.copy(pluginSource, destDirFile, true);
        }
        if (isDelTemp) {
            FileUtil.del(pluginTempDir);
        }
        return destDirFile;
    }

    /**
     * 解压jar格式的插件
     * @param pluginFile pluginFile
     * @param tempDir 临时处理目录
     * @param pluginBaseDir 插件存放基础目录
     * @throws IOException
     */
    public static File extractJarPlugin (File pluginFile, String tempDir, String pluginBaseDir) throws Exception {
        File dir = new File(tempDir + File.separator + "pluginHandle" + File.separator +  pluginFile.getName());
        FileUtil.mkdir(dir);
        Enumeration<JarEntry> jarEntries;
        try (JarFile jarFile = new JarFile(pluginFile)) {
            jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry entry = jarEntries.nextElement();
                if (entry.getName().endsWith("/")) {
                    File dirFile = new File(dir + File.separator + entry.getName());
                    FileUtil.mkdir(dirFile);
                } else {
                    File file = new File(dir + File.separator + entry.getName());
                    InputStream inputStream = jarFile.getInputStream(entry);
                    OutputStream outputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                }
            }
            return dir;
        }
    }


    /**
     * 解压压缩包形式的插件
     * @param pluginFile pluginFile
     * @param tempDir 临时目录
     * @param pluginBaseDir 插件存放基础目录
     * @return File
     */
    public static File extractZipPlugin(File pluginFile, String tempDir, String pluginBaseDir) {
        return null;
    }


    /**
     * 获取插件中所有的class集合
     * @author perfree
     * @date 2023-09-27 16:09:53
     * @param pluginDir 插件目录
     * @param classLoader 插件classLoader
     * @return java.util.List<java.lang.Class < ?>>
     */
    public static List<Class<?>> getClassList(File pluginDir, ClassLoader classLoader) {
        List<Class<?>> classList = new ArrayList<>();
        if (!pluginDir.exists()) {
            return classList;
        }
        List<File> files = FileUtil.loopFiles(pluginDir);
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                String entryName = file.getAbsolutePath().replace(pluginDir.getAbsolutePath(), "").replaceAll("[\\\\/]", ".");
                if (entryName.startsWith(".")) {
                    entryName = entryName.replaceFirst(".", "");
                }
                String className = entryName.substring(0, entryName.length() - 6);
                Class<?> aClass;
                try {
                    aClass = classLoader.loadClass(className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                classList.add(aClass);
            }
        }
        return classList;
    }

    /**
     * 获取插件中所有的mapperXml
     * @param pluginDir pluginDir
     * @param pluginBaseConfig pluginBaseConfig
     * @return List<File>
     */
    public static List<File> getMapperXml(File pluginDir, PluginBaseConfig pluginBaseConfig) {
        String xmlLocationPattern = pluginBaseConfig.getPlugin().getMapperLocation()
                .replaceAll("\\*\\*", "<>")
                .replaceAll("\\*", "<>")
                .replaceAll("\\.", "\\.")
                .replaceAll("<>", ".*");

        List<File> files = FileUtil.loopFiles(pluginDir);
        List<File> result = new ArrayList<>();
        for (File file : files) {
            String realPath = file.getAbsolutePath().replace(pluginDir.getAbsolutePath() + File.separator, "").replaceAll("\\\\","/");
            if (Pattern.matches(xmlLocationPattern, realPath) && file.getName().endsWith(".xml")) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * 获取插件中所有的mapperXml路径
     * @param pluginDir pluginDir
     * @param pluginBaseConfig pluginBaseConfig
     * @return List<String>
     */
    public static List<String> getMapperXmlPath(File pluginDir, PluginBaseConfig pluginBaseConfig) {
        String xmlLocationPattern = pluginBaseConfig.getPlugin().getMapperLocation()
                .replaceAll("\\*\\*", "<>")
                .replaceAll("\\*", "<>")
                .replaceAll("\\.", "\\.")
                .replaceAll("<>", ".*");

        List<File> files = FileUtil.loopFiles(pluginDir);
        List<String> result = new ArrayList<>();
        for (File file : files) {
            String realPath = file.getAbsolutePath().replace(pluginDir.getAbsolutePath() + File.separator, "").replaceAll("\\\\","/");
            if (Pattern.matches(xmlLocationPattern, realPath) && file.getName().endsWith(".xml")) {
                result.add(file.getAbsolutePath());
            }
        }
        return result;
    }
}
