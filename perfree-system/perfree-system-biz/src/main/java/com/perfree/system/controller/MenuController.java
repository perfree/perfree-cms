package com.perfree.system.controller;


import com.perfree.commons.common.CommonResult;
import com.perfree.system.convert.menu.MenuConvert;
import com.perfree.system.model.Menu;
import com.perfree.system.service.menu.MenuService;
import com.perfree.system.vo.menu.MenuAddOrUpdateReqVO;
import com.perfree.system.vo.menu.MenuListReqVO;
import com.perfree.system.vo.menu.MenuRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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

    @PostMapping("/list")
    @Operation(summary = "菜单列表")
    public CommonResult<List<MenuRespVO>> list(@RequestBody MenuListReqVO pageVO) {
        List<Menu> menuList = menuService.menuList(pageVO);
        return success(MenuConvert.INSTANCE.convertListVO(menuList));
    }

    @GetMapping("/get")
    @Operation(summary = "获取菜单")
    public CommonResult<MenuRespVO> list(@RequestParam(value = "id") String id) {
        Menu menu = menuService.getById(id);
        return success(MenuConvert.INSTANCE.convertRespVO(menu));
    }

    @PostMapping("/addOrUpdate")
    @Operation(summary = "添加或更新")
    public CommonResult<MenuRespVO> addOrUpdate(@RequestBody @Valid MenuAddOrUpdateReqVO menuAddOrUpdateReqVO) {
        Menu menu = menuService.addOrUpdate(menuAddOrUpdateReqVO);
        return success(MenuConvert.INSTANCE.convertRespVO(menu));
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除菜单")
    public CommonResult<Boolean> del(@RequestParam(value = "id") String id) {
        return success(menuService.del(id));
    }

}
