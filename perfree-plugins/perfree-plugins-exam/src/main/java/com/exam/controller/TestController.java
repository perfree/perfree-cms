package com.exam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @title PerfreeBlog
 * @description // TODO
 * @author Perfree
 * @version 1.0.0
 * @create 2023/9/26 11:58
 **/
@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping("hello")
    public String hello(){
        LOGGER.info("测试日志~");
        return "Hello";
    }
}
