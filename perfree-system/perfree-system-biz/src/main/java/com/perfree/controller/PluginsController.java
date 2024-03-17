package com.perfree.controller;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.convert.plugins.PluginsConvert;
import com.perfree.model.Plugins;
import com.perfree.service.plugins.PluginsService;
import com.perfree.vo.plugins.PluginsPageReqVO;
import com.perfree.vo.plugins.PluginsRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.perfree.commons.common.CommonResult.success;

@RestController
@Tag(name = "插件相关接口")
@RequestMapping("api/plugins")
public class PluginsController {

    @Resource
    private PluginsService pluginsService;

    @PostMapping("/page")
    @Operation(summary = "插件分页列表")
    public CommonResult<PageResult<PluginsRespVO>> page(@RequestBody PluginsPageReqVO pageVO) {
        PageResult<Plugins> pluginsPageResult = pluginsService.pluginsPage(pageVO);
        return success(PluginsConvert.INSTANCE.convertPageResultVO(pluginsPageResult));
    }


    @PostMapping("/installPlugin")
    @Operation(summary = "插件安装")
    public CommonResult<Boolean> installPlugin(MultipartFile file) {
        return success(pluginsService.installPlugin(file));
    }
}
