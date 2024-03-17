package org.pf4j.demo.perfree.controller;

import com.perfree.commons.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.pf4j.demo.perfree.commons.TestConfig;
import org.pf4j.demo.perfree.convert.TestConvert;
import org.pf4j.demo.perfree.vo.test.TestRespVO;
import org.pf4j.demo.perfree.service.test.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private TestConfig testConfig;


    @GetMapping("/get")
    @Operation(summary = "获取信息")
    public CommonResult<TestRespVO> get(@RequestParam(value = "id") Integer id) {
        return success(TestConvert.INSTANCE.convertRespVO(testService.getById(id)));
    }
}
