package com.perfree.controller;

import com.perfree.commons.common.CommonResult;
import com.perfree.vo.LoginUserReqVO;
import com.perfree.vo.LoginUserRespVO;
import com.perfree.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 系统基础接口
 * @author Perfree
 * @version 1.0.0
 * @create 2023/9/28 10:16
 **/
@RestController
@Tag(name = "系统基础接口")
@RequiredArgsConstructor
@RequestMapping("api")
public class SystemController {

    private final IUserService userService;


    @PostMapping("login")
    @Operation(summary = "使用账号密码登录")
    public CommonResult<LoginUserRespVO> login(@RequestBody LoginUserReqVO loginUserVO){
        return success(userService.login(loginUserVO));
    }
}
