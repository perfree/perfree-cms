package com.perfree.service.menu;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.commons.constant.SystemConstants;
import com.perfree.commons.enums.MenuTypeEnum;
import com.perfree.commons.utils.SecurityFrameworkUtils;
import com.perfree.convert.menu.MenuConvert;
import com.perfree.mapper.MenuMapper;
import com.perfree.model.Menu;
import com.perfree.model.User;
import com.perfree.vo.system.MenuTreeListRespVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuTreeListRespVO> menuAdminListByLoginUser() {
        User loginUser = SecurityFrameworkUtils.getLoginUser();
        assert loginUser != null;
        List<Menu> menuList = menuMapper.menuListByUserId(loginUser.getId(), MenuTypeEnum.ADMIN.getType());
        List<MenuTreeListRespVO> menuTreeListRespVOS = MenuConvert.INSTANCE.convertTreeList(menuList);
        // 获取所有跟节点
        List<MenuTreeListRespVO> result = menuTreeListRespVOS.stream().filter(menu -> menu.getParentId().equals(SystemConstants.ROOT_MENU_CODE)).toList();
        // 将原数组中所有根节点移除
        menuTreeListRespVOS.removeIf(menu -> menu.getParentId().equals(SystemConstants.ROOT_MENU_CODE));
        for (MenuTreeListRespVO menu : result) {
            buildChildMenu(menu, menuTreeListRespVOS);
        }
        return result;
    }

    /**
     * 生成子菜单
     * @param menuTreeListRespVO 父级菜单信息
     * @param queryMenuTreeList 菜单集合
     */
    private void buildChildMenu(MenuTreeListRespVO menuTreeListRespVO, List<MenuTreeListRespVO> queryMenuTreeList) {
        List<MenuTreeListRespVO> children = new ArrayList<>();
        for (MenuTreeListRespVO treeListRespVO : queryMenuTreeList) {
            if (treeListRespVO.getParentId().equals(menuTreeListRespVO.getId())) {
                children.add(treeListRespVO);
                buildChildMenu(treeListRespVO, queryMenuTreeList);
            }
        }
        menuTreeListRespVO.setChildren(children);
    }
}
