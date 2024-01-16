package com.perfree.system.service.menu;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.commons.constant.SystemConstants;
import com.perfree.commons.enums.MenuTypeEnum;
import com.perfree.commons.exception.ServiceException;
import com.perfree.security.SecurityFrameworkUtils;
import com.perfree.security.vo.LoginUserVO;
import com.perfree.system.convert.menu.MenuConvert;
import com.perfree.system.mapper.MenuMapper;
import com.perfree.system.mapper.RoleMenuMapper;
import com.perfree.system.model.Menu;
import com.perfree.system.vo.menu.MenuAddOrUpdateReqVO;
import com.perfree.system.vo.menu.MenuListReqVO;
import com.perfree.system.vo.system.MenuTreeListRespVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.enums.ErrorCode.MENU_EXISTS_CHILDREN;

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

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<MenuTreeListRespVO> menuAdminListByLoginUser() {
        LoginUserVO loginUser = SecurityFrameworkUtils.getLoginUser();
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

    @Override
    public List<Menu> menuList(MenuListReqVO pageVO) {
        return menuMapper.menuList(pageVO);
    }

    @Override
    public Menu addOrUpdate(MenuAddOrUpdateReqVO menuAddOrUpdateReqVO) {
        Menu menu = MenuConvert.INSTANCE.convertMenu(menuAddOrUpdateReqVO);
        if (StringUtils.isNotBlank(menu.getId())) {
            menuMapper.updateById(menu);
        } else {
            menu.setId(IdUtil.simpleUUID());
            menuMapper.insert(menu);
        }
        return menu;
    }

    @Override
    public Boolean del(String id) {
        List<Menu> menuList = menuMapper.getByParentId(id);
        if (!menuList.isEmpty()){
            throw new ServiceException(MENU_EXISTS_CHILDREN);
        }
        menuMapper.deleteById(id);
        roleMenuMapper.deleteByMenuId(id);
        return true;
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
