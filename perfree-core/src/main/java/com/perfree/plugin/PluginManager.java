package com.perfree.plugin;

import com.perfree.plugin.commons.PluginUtils;
import com.perfree.plugin.exception.PluginException;
import com.perfree.plugin.handle.compound.PluginHandle;
import com.perfree.plugin.pojo.PluginBaseConfig;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Perfree
 * @description 插件管理类,提供插件安装/卸载/运行/停止等方法
 * @date 15:36 2023/9/28
 */
@Component
public class PluginManager{
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginManager.class);

    @Value("${perfree.plugin-dir}")
    private String pluginBaseDir;

    @Value("${perfree.temp-dir}")
    private String tempDir;

    @Resource
    private PluginHandle pluginHandle;

    /**
     * 初始化所有插件
     * @author perfree
     * @date 2023-09-27 16:09:44
     */
    public void initPlugins() {
        File pluginBaseDirFile = new File(pluginBaseDir);
        if (!pluginBaseDirFile.exists()) {
           return;
        }
        File[] files = pluginBaseDirFile.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                runPlugin(file);
            }
        }
    }

    /**
     * 运行插件
     * @author perfree
     * @date 2023-09-27 16:09:02
     * @param pluginDir 插件目录
     */
    public void runPlugin(File pluginDir) {
        try {
            PluginInfo pluginInfo = pluginHandle.startPlugin(pluginDir);
            BasePluginEvent bean = PluginApplicationContextHolder.getPluginBean(pluginInfo.getPluginId(), BasePluginEvent.class);
            if (null != bean) {
                bean.onStart();
            }
        } catch (Exception e) {
            LOGGER.error("plugin  ----->  start error:{}", e.getMessage(), e);
        }
    }

    /**
     * 停止插件
     * @author perfree
     * @date 2023-09-27 16:09:24
     * @param pluginId 插件id
     */
    public void stopPlugin(String pluginId) {
        try {
            PluginInfo pluginInfo = PluginInfoHolder.getPluginInfo(pluginId);
            BasePluginEvent bean = PluginApplicationContextHolder.getPluginBean(pluginInfo.getPluginId(), BasePluginEvent.class);
            if (null != bean) {
                bean.onStop();
            }
            pluginHandle.stopPlugin(pluginId);
        } catch (Exception e) {
            LOGGER.error("plugin  ----->  stop error:{}", e.getMessage(), e);
        }
    }

    /**
     * 安装插件
     * @param pluginFile pluginFile
     */
    public void installPlugin(File pluginFile) throws Exception {
        if (null == pluginFile || !pluginFile.exists()) {
            throw new PluginException("pluginFile not found!");
        }
        File pluginTempDir;
        if (pluginFile.getName().endsWith(".jar")) {
            pluginTempDir = PluginUtils.extractJarPlugin(pluginFile, tempDir, pluginBaseDir);
        }else {
            pluginTempDir = PluginUtils.extractZipPlugin(pluginFile, tempDir, pluginBaseDir);
        }
        if (null == pluginTempDir) {
            throw new PluginException("plugin extract fail!");
        }

        PluginBaseConfig pluginConfig = PluginUtils.getPluginConfig(pluginTempDir);
        if (null == pluginConfig) {
            throw new PluginException("plugin.yaml parse fail");
        }

        Boolean update = PluginUtils.isUpdate(pluginConfig, pluginBaseDir);
        if (update && PluginInfoHolder.getPluginInfo(pluginConfig.getPlugin().getName()) != null) {
            pluginHandle.stopPlugin(pluginConfig.getPlugin().getName());
        }
        File pluginDir = PluginUtils.copyPluginTempToPlugin(pluginTempDir, pluginBaseDir);
        PluginInfo pluginInfo = pluginHandle.startPlugin(pluginDir);
        BasePluginEvent bean = PluginApplicationContextHolder.getPluginBean(pluginInfo.getPluginId(), BasePluginEvent.class);
        if (null != bean) {
            if (update) {
                bean.onUpdate();
            } else {
                bean.onInstall();
            }
        }
        pluginHandle.stopPlugin(pluginInfo.getPluginId());
    }
}
