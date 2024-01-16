package com.perfree.system.service.role;

import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.system.model.RoleMenu;
import com.perfree.system.vo.role.RoleMenuReqVO;
import com.perfree.system.vo.role.RolePageReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface RoleService extends IService<Role> {

    /**
     * 角色分页
     * @param pageVO pageVO
     * @return PageResult<Role>
     */
    PageResult<Role> rolePage(RolePageReqVO pageVO);


    /**
     * 根据角色id获取拥有的菜单list
     * @param id id
     * @return List<RoleMenu>
     */
    List<RoleMenu> getRoleMenus(Integer id);

    /**
     * 设置角色菜单权限
     * @param roleMenuReqVO roleMenuReqVO
     * @return Boolean
     */
    Boolean assignRoleMenu(RoleMenuReqVO roleMenuReqVO);
}
