package com.perfree.system.controller;


import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.perfree.system.service.codegen.CodegenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description 代码生成
 * @author Perfree
 * @version 1.0.0
 * @create 2023/1/15 10:16
 **/
@RestController
@Tag(name = "代码生成相关接口")
@RequestMapping("api/codegen")
public class CodegenController {

    @Resource
    private CodegenService codegenService;

    @PostMapping("/getTableList")
    @Operation(summary = "获取数据库所有的表")
    public List<TableInfo> getTableList() {
        return codegenService.getTableList();
    }
}
