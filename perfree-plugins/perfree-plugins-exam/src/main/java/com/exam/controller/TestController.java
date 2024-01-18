package com.exam.controller;

import com.exam.convert.TestConvert;
import com.exam.model.Test;
import com.exam.service.test.TestService;
import com.exam.vo.test.TestAddOrUpdateReqVO;
import com.exam.vo.test.TestPageReqVO;
import com.exam.vo.test.TestRespVO;
import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/***
 * @title PerfreeBlog
 * @description // TODO
 * @author Perfree
 * @version 1.0.0
 * @create 2023/9/26 11:58
 **/
@RestController
@Tag(name = "插件测试接口")
@RequestMapping("api/plugin/test")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Resource
    private TestService testService;


    @PostMapping("/page")
    @Operation(summary = "分页列表")
    public CommonResult<PageResult<TestRespVO>> page(@RequestBody TestPageReqVO pageVO) {
        PageResult<Test> testPageResult = testService.testPage(pageVO);
        return success(TestConvert.INSTANCE.convertPageResultVO(testPageResult));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有")
    public CommonResult<List<TestRespVO>> hello(){
        LOGGER.info("测试2日志~");
        return success(TestConvert.INSTANCE.convertRespListVO(testService.test()));
    }

    @GetMapping("/get")
    @Operation(summary = "获取信息")
    public CommonResult<TestRespVO> get(@RequestParam(value = "id") Integer id) {
        return success(TestConvert.INSTANCE.convertRespVO(testService.getById(id)));
    }

    @PostMapping("/addOrUpdate")
    @Operation(summary = "添加或更新")
    public CommonResult<TestRespVO> addOrUpdate(@RequestBody @Valid TestAddOrUpdateReqVO testAddOrUpdateReqVO) {
        return success(TestConvert.INSTANCE.convertRespVO(testService.addOrUpdate(testAddOrUpdateReqVO)));
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除角色")
    public CommonResult<Boolean> del(@RequestParam(value = "id") Integer id) {
        return success(testService.removeById(id));
    }

}
