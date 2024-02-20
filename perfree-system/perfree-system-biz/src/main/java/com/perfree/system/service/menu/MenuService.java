package com.perfree.system.service.menu;

import com.perfree.system.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.system.vo.menu.MenuAddOrUpdateReqVO;
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

    /**
     * 添加或更新
     * @param menuAddOrUpdateReqVO menuAddOrUpdateReqVO
     * @return Menu
     */
    Menu addOrUpdate(MenuAddOrUpdateReqVO menuAddOrUpdateReqVO);

    /**
     * 删除菜单
     * @param id id
     * @return Boolean
     */
    Boolean del(String id);

    /**
     * 根据用户id获取用户权限
     * @param userId userId
     * @return List<String>
     */
    List<String> getPermissionByUserId(Integer userId);

}
