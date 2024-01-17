package com.perfree.config;

import com.perfree.plugin.PluginDevManager;
import com.perfree.plugin.PluginManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Perfree
 * @description 初始化执行
 * @date 15:39 2023/9/28
 */
@Component
public class AppInit implements ApplicationRunner {

    private final PluginManager pluginManager;

    private final PluginDevManager pluginDevManager;

    public AppInit(PluginManager pluginManager, PluginDevManager pluginDevManager) {
        this.pluginManager = pluginManager;
        this.pluginDevManager = pluginDevManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pluginManager.initPlugins();
        String command = System.getProperty("sun.java.command");
        if (command != null && !command.contains(".jar")) {
            // 源码运行,加载本地插件
            pluginDevManager.initPlugins();
        }
    }
}
