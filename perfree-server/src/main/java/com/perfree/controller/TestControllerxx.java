package com.perfree.controller;

import com.perfree.commons.common.CommonResult;
import com.perfree.plugin.PluginManagerHandle;
import com.perfree.plugin.handle.compound.PluginHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.pf4j.PluginWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.perfree.commons.common.CommonResult.success;

@RestController
@Tag(name = "插件测试接口2")
@RequestMapping("api/plugin/test2")
public class TestControllerxx {

    @Resource
    private PluginManagerHandle pluginManagerHandle;

    @Resource
    private PluginHandle pluginHandle;

    @GetMapping("/stop")
    @Operation(summary = "停止")
    public CommonResult<Boolean> stop() throws Exception {
        for (PluginWrapper pluginWrapper : pluginManagerHandle.getPlugins()) {
            pluginHandle.stopPlugin(pluginWrapper.getPluginId());
        }
        return success(true);
    }


    @GetMapping("/start")
    @Operation(summary = "启动")
    public CommonResult<Boolean> get() throws Exception {
        for (PluginWrapper pluginWrapper : pluginManagerHandle.getPlugins()) {
            pluginHandle.startPlugin(pluginWrapper.getPluginId());
        }
        return success(true);
    }
}
