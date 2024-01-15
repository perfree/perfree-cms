package com.perfree.system.service.menu;

import com.perfree.system.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.system.vo.menu.MenuListReqVO;
import com.perfree.system.vo.system.MenuTreeListRespVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取当前登录用户拥有的后台管理菜单
     */
    List<MenuTreeListRespVO> menuAdminListByLoginUser();

    /**
     * 菜单列表
     * @param pageVO pageVO
     * @return List<Menu> 菜单列表
     */
    List<Menu> menuList(MenuListReqVO pageVO);

}
