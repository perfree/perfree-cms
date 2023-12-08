package com.perfree.service.menu;

import com.perfree.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.vo.system.MenuTreeListRespVO;

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
}
