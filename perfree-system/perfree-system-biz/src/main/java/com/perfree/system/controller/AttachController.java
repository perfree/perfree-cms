package com.perfree.system.controller;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.system.convert.attach.AttachConvert;
import com.perfree.system.model.Attach;
import com.perfree.system.service.attach.AttachService;
import com.perfree.system.vo.attach.AttachPageReqVO;
import com.perfree.system.vo.attach.AttachRespVO;
import com.perfree.system.vo.attach.AttachUploadVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 附件管理
 * @author Perfree
 * @version 1.0.0
 * @create 2023/1/15 10:16
 **/
@RestController
@Tag(name = "附件相关接口")
@RequestMapping("api/attach")
public class AttachController {

    @Resource
    private AttachService attachService;

    @PostMapping("/upload")
    @Operation(summary = "附件上传")
    public CommonResult<AttachRespVO> upload(AttachUploadVO attachUploadVO) {
        Attach attach = attachService.create(attachUploadVO);
        return success(AttachConvert.INSTANCE.convertRespVO(attach));
    }

    @PostMapping("/page")
    @Operation(summary = "附件分页列表")
    public CommonResult<PageResult<AttachRespVO>> page(@RequestBody AttachPageReqVO pageVO) {
        PageResult<Attach> rolePageResult = attachService.attachPage(pageVO);
        return success(AttachConvert.INSTANCE.convertPageResultVO(rolePageResult));
    }

    @PostMapping("/del")
    @Operation(summary = "删除附件")
    public CommonResult<Boolean> del(@RequestParam(value = "id") Integer id) {
        return success(attachService.del(id));
    }
}
