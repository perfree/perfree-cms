package com.perfree.config;

import com.perfree.plugin.PluginDevManager;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.PluginInfoHolder;
import com.perfree.plugin.PluginManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Perfree
 * @description 初始化执行
 * @date 15:39 2023/9/28
 */
@Component
public class AppInit implements ApplicationRunner {

    @Value("${server.port}")
    private String port;


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
        List<PluginInfo> pluginInfoList = PluginInfoHolder.getAllPluginInfo();
        StringBuilder successPluginStr = new StringBuilder();
        pluginInfoList.forEach(pluginInfo -> successPluginStr.append("Success Load Plugin -> ").append(pluginInfo.getPluginPath()).append("\n"));
        String banner = """
                ----------------------------------------------------------------------------------
                                         __                     \s
                                        / _|                    \s
                  _ __     ___   _ __  | |_   _ __    ___    ___\s
                 | '_ \\   / _ \\ | '__| |  _| | '__|  / _ \\  / _ \\
                 | |_) | |  __/ | |    | |   | |    |  __/ |  __/
                 | .__/   \\___| |_|    |_|   |_|     \\___|  \\___|
                 | |                                            \s
                 |_|                                            \s
                 
                 %s
                 Successfully started!
                 access port: %s
                ----------------------------------------------------------------------------------
                """.formatted(successPluginStr, port);
        System.out.println(banner);
    }
}
