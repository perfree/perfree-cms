package com.perfree.service.menu;

import com.perfree.commons.enums.MenuTypeEnum;
import com.perfree.commons.utils.SecurityFrameworkUtils;
import com.perfree.model.Menu;
import com.perfree.mapper.MenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    public void menuAdminListByLoginUser() {
        User loginUser = SecurityFrameworkUtils.getLoginUser();
        assert loginUser != null;
        List<Menu> menuList = menuMapper.menuListByUserId(loginUser.getId(), MenuTypeEnum.ADMIN.getType());
    }
}
