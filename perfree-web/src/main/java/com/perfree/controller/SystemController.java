package com.perfree.controller;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.utils.SecurityFrameworkUtils;
import com.perfree.model.User;
import com.perfree.service.menu.MenuService;
import com.perfree.service.user.UserService;
import com.perfree.vo.system.LoginUserReqVO;
import com.perfree.vo.system.LoginUserRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
@RequestMapping("api")
public class SystemController {

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;


    @PostMapping("login")
    @Operation(summary = "使用账号密码登录")
    public CommonResult<LoginUserRespVO> login(@RequestBody LoginUserReqVO loginUserVO){
        return success(userService.login(loginUserVO));
    }


    @PostMapping("menuList")
    @Operation(summary = "获取当前账号拥有的菜单")
    public CommonResult<String> menuList(){
        menuService.menuAdminListByLoginUser();
        return success("");
    }
}
