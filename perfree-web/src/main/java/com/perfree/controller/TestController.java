package com.perfree.controller;

import cn.hutool.core.io.FileUtil;
import com.perfree.plugin.PluginManager;
import com.perfree.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/unInstallJar")
    @Operation(summary = "卸载插件jar")
    @PreAuthorize(value = "hasAuthority('admin')")
    public String unInstallJar() {
        List<File> jarFiles = FileUtil.loopFiles("E:\\111", file -> file.getName().toLowerCase().endsWith(".jar"));
        int id = 1;
        for (File pluginJarFile : jarFiles) {
            pluginManager.stopPluginJar(pluginJarFile, id);
            id++;
        }
        return "111";
    }
}
