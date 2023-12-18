package com.perfree.config;

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

    public AppInit(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pluginManager.initPlugins();
    }
}
