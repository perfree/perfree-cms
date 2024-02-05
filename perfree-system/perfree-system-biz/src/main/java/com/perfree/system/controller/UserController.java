package com.perfree.system.controller;

import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.system.convert.role.RoleConvert;
import com.perfree.system.convert.user.UserConvert;
import com.perfree.system.model.Role;
import com.perfree.system.model.User;
import com.perfree.system.service.user.UserService;
import com.perfree.system.vo.role.RolePageReqVO;
import com.perfree.system.vo.role.RoleRespVO;
import com.perfree.system.vo.user.UserPageReqVO;
import com.perfree.system.vo.user.UserRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 用户
 * @author Perfree
 * @version 1.0.0
 **/
@RestController
@Tag(name = "用户相关接口")
@RequestMapping("api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/page")
    @Operation(summary = "用户分页列表")
    public CommonResult<PageResult<UserRespVO>> page(@RequestBody UserPageReqVO pageVO) {
        PageResult<User> userPageResult = userService.userPage(pageVO);
        return success(UserConvert.INSTANCE.convertPageResultVO(userPageResult));
    }
}
