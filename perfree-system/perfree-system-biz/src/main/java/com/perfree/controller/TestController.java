package com.perfree.controller;

import cn.hutool.core.io.FileUtil;
import com.perfree.plugin.PluginManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @author Perfree
 * @description 测试接口, 用于验证
 * @date 15:39 2023/9/28
 */
@RestController
@Tag(name = "测试接口")
@RequiredArgsConstructor
public class TestController {

    private final PluginManager pluginManager;

    @Value("${perfree.plugin-dir}")
    private String pluginBaseDir;

    @GetMapping("/unInstallJar")
    @Operation(summary = "卸载插件jar")
    public String unInstallJar() {
        pluginManager.stopPlugin("perfree-exam123");
        return "111";
    }

    @GetMapping("/start")
    @Operation(summary = "启动插件jar")
    public String start() {
        pluginManager.runPlugin(new File(pluginBaseDir + "/perfree-exam123"));
        return "111";
    }
}
