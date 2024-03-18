package com.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.exam.commons.TestConfig;
import com.exam.convert.TestConvert;
import com.exam.service.test.TestService;
import com.exam.vo.test.TestRespVO;
import com.perfree.commons.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    @Resource
    private TestConfig testConfig;


    @GetMapping("/get")
    @Operation(summary = "获取信息")
    public CommonResult<Boolean> get(@RequestParam(value = "id") Integer id) {
        String fileName = "E:\\" + "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
        return success(true);
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }


    @GetMapping("/queryByMapperXml")
    @Operation(summary = "mapperXml查询")
    public CommonResult<List<TestRespVO>> queryByMapperXml(){
        return success(TestConvert.INSTANCE.convertRespListVO(testService.queryByMapperXml()));
    }

    @GetMapping("/queryByConfigBean")
    @Operation(summary = "queryByConfigBean")
    public CommonResult<String> queryByConfigBean(){
        return success(testConfig.getName());
    }

}
