package com.perfree.system.controller;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Role;
import com.perfree.system.model.RoleMenu;
import com.perfree.system.service.role.RoleService;
import com.perfree.system.vo.role.RoleMenuReqVO;
import com.perfree.system.vo.role.RolePageReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 角色
 * @author Perfree
 * @version 1.0.0
 * @create 2023/1/16 10:16
 **/
@RestController
@Tag(name = "角色相关接口")
@RequestMapping("api/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/page")
    @Operation(summary = "角色分页列表")
    public CommonResult<PageResult<Role>> page(@RequestBody RolePageReqVO pageVO) {
        return success(roleService.rolePage(pageVO));
    }

    @GetMapping("/getRoleMenus")
    @Operation(summary = "获取角色所拥有的菜单列表")
    public CommonResult<List<String>> getRoleMenus(@RequestParam(value = "id") Integer id) {
        List<RoleMenu> roleMenus = roleService.getRoleMenus(id);
        List<String> result = new ArrayList<>();
        for (RoleMenu roleMenu : roleMenus) {
            result.add(roleMenu.getMenuId());
        }
        return success(result);
    }

    @PostMapping("/assignRoleMenu")
    @Operation(summary = "设置角色菜单权限")
    public CommonResult<Boolean> assignRoleMenu(@RequestBody @Valid RoleMenuReqVO roleMenuReqVO) {
        return success(roleService.assignRoleMenu(roleMenuReqVO));
    }
}
