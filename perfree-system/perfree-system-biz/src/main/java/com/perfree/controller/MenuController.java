package com.perfree.controller;


import com.perfree.commons.common.CommonResult;
import com.perfree.convert.menu.MenuConvert;
import com.perfree.model.Menu;
import com.perfree.service.menu.MenuService;
import com.perfree.vo.menu.MenuAddOrUpdateReqVO;
import com.perfree.vo.menu.MenuListReqVO;
import com.perfree.vo.menu.MenuRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 菜单
 * @author Perfree
 * @version 1.0.0
 * @create 2023/1/15 10:16
 **/
@RestController
@Tag(name = "菜单相关接口")
@RequestMapping("api/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/page")
    @Operation(summary = "菜单页面列表")
    @PreAuthorize("@ss.hasPermission('admin:menu:page')")
    public CommonResult<List<MenuRespVO>> page(@RequestBody MenuListReqVO pageVO) {
        List<Menu> menuList = menuService.menuList(pageVO);
        return success(MenuConvert.INSTANCE.convertListVO(menuList));
    }

    @PostMapping("/list")
    @Operation(summary = "菜单列表")
    public CommonResult<List<MenuRespVO>> list(@RequestBody MenuListReqVO pageVO) {
        List<Menu> menuList = menuService.menuList(pageVO);
        return success(MenuConvert.INSTANCE.convertListVO(menuList));
    }

    @GetMapping("/get")
    @Operation(summary = "获取菜单")
    @PreAuthorize("@ss.hasPermission('admin:menu:get')")
    public CommonResult<MenuRespVO> list(@RequestParam(value = "id") String id) {
        Menu menu = menuService.getById(id);
        return success(MenuConvert.INSTANCE.convertRespVO(menu));
    }

    @PostMapping("/add")
    @Operation(summary = "添加菜单")
    @PreAuthorize("@ss.hasPermission('admin:menu:create')")
    public CommonResult<MenuRespVO> add(@RequestBody @Valid MenuAddOrUpdateReqVO menuAddOrUpdateReqVO) {
        Menu menu = menuService.addOrUpdate(menuAddOrUpdateReqVO);
        return success(MenuConvert.INSTANCE.convertRespVO(menu));
    }

    @PostMapping("/update")
    @Operation(summary = "更新菜单")
    @PreAuthorize("@ss.hasPermission('admin:menu:update')")
    public CommonResult<MenuRespVO> update(@RequestBody @Valid MenuAddOrUpdateReqVO menuAddOrUpdateReqVO) {
        Menu menu = menuService.addOrUpdate(menuAddOrUpdateReqVO);
        return success(MenuConvert.INSTANCE.convertRespVO(menu));
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除菜单")
    @PreAuthorize("@ss.hasPermission('admin:menu:del')")
    public CommonResult<Boolean> del(@RequestParam(value = "id") String id) {
        return success(menuService.del(id));
    }

}
