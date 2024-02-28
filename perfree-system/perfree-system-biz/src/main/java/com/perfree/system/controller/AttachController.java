package com.perfree.system.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.system.convert.attach.AttachConvert;
import com.perfree.system.model.Attach;
import com.perfree.system.service.attach.AttachService;
import com.perfree.system.vo.attach.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachController.class);
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

    @GetMapping("/get")
    @Operation(summary = "获取附件")
    public CommonResult<AttachRespVO> get(@RequestParam(value = "id") Integer id) {
        Attach byId = attachService.getById(id);
        return success(AttachConvert.INSTANCE.convertRespVO(byId));
    }

    @PutMapping("/update")
    @Operation(summary = "修改附件")
    public CommonResult<Boolean> update(@RequestBody AttachUpdateVO attachUpdateVO) {
        return success(attachService.updateAttach(attachUpdateVO));
    }


    @DeleteMapping("/del")
    @Operation(summary = "删除附件")
    public CommonResult<Boolean> del(@RequestParam(value = "id") Integer id) {
        return success(attachService.del(id));
    }


    @GetMapping("/getAllAttachGroup")
    @Operation(summary = "获取所有附件分组")
    public CommonResult<List<AttachGroupRespVO>> getAllAttachGroup() {
        List<Attach> attachList = attachService.getAllAttachGroup();
        return success(AttachConvert.INSTANCE.convertGroupRespVO(attachList));
    }


    @GetMapping("/file/{configId}/get/**")
    @Operation(summary = "获取文件")
    public void getFileContent(HttpServletRequest request, HttpServletResponse response, @PathVariable("configId") Integer configId) throws IOException {
        // 获取请求的路径
        String path = StrUtil.subAfter(request.getRequestURI(), "/get/", false);
        if (StrUtil.isEmpty(path)) {
            throw new IllegalArgumentException("结尾的 path 路径必须传递");
        }
        byte[] content = attachService.getFileContent(configId, path);
        if (content == null) {
            LOGGER.warn("[getFileContent][configId({}) path({}) 文件不存在]", configId, path);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        // 设置 header 和 contentType
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(path, StandardCharsets.UTF_8));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // 输出附件
        IoUtil.write(response.getOutputStream(), false, content);
    }
}
