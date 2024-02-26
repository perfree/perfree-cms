package com.perfree.system.controller;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.system.convert.attachConfig.AttachConfigConvert;
import com.perfree.system.model.AttachConfig;
import com.perfree.system.service.attachConfig.AttachConfigService;
import com.perfree.system.vo.attachConfig.AttachConfigCreateVO;
import com.perfree.system.vo.attachConfig.AttachConfigPageReqVO;
import com.perfree.system.vo.attachConfig.AttachConfigRespVO;
import com.perfree.system.vo.attachConfig.AttachConfigUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

@RestController
@Tag(name = "附件服务器配置相关接口")
@RequestMapping("api/attachConfig")
public class AttachConfigController {

    @Resource
    private AttachConfigService attachConfigService;

    @PostMapping("/getAll")
    @Operation(summary = "获取所有配置")
    public CommonResult<List<AttachConfigRespVO>> getAll() {
        List<AttachConfig> attachConfigList = attachConfigService.getAll();
        return CommonResult.success(AttachConfigConvert.INSTANCE.convertRespListVO(attachConfigList));
    }

    @PostMapping("/page")
    @Operation(summary = "配置分页列表")
    public CommonResult<PageResult<AttachConfigRespVO>> page(@RequestBody AttachConfigPageReqVO pageVO) {
        PageResult<AttachConfig> attachPage = attachConfigService.attachConfigPage(pageVO);
        return success(AttachConfigConvert.INSTANCE.convertPageResultVO(attachPage));
    }

    @PostMapping("/add")
    @Operation(summary = "新增配置")
    public CommonResult<AttachConfigRespVO> add(@RequestBody @Valid AttachConfigCreateVO attachConfigCreateVO) {
        AttachConfig attachConfig = attachConfigService.add(attachConfigCreateVO);
        return CommonResult.success(AttachConfigConvert.INSTANCE.convertRespVO(attachConfig));
    }


    @PutMapping("/update")
    @Operation(summary = "修改配置")
    public CommonResult<Boolean> update(@RequestBody @Valid AttachConfigUpdateVO attachConfigUpdateVO) {
        return CommonResult.success(attachConfigService.updateAttachConfig(attachConfigUpdateVO));
    }


    @GetMapping("/get")
    @Operation(summary = "根据id获取配置")
    public CommonResult<AttachConfigRespVO> get(@RequestParam(value = "id") Integer id) {
        AttachConfig attachConfig = attachConfigService.getById(id);
        return CommonResult.success(AttachConfigConvert.INSTANCE.convertRespVO(attachConfig));
    }

    @DeleteMapping("/del")
    @Operation(summary = "根据id删除配置")
    public CommonResult<Boolean> del(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(attachConfigService.del(id));
    }
}
