package com.perfree.system.service.role;

import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.system.model.RoleMenu;
import com.perfree.system.vo.role.RoleAddOrUpdateReqVO;
import com.perfree.system.vo.role.RoleMenuReqVO;
import com.perfree.system.vo.role.RolePageReqVO;
import com.perfree.system.vo.role.RoleRespVO;

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

    /**
     * 获取角色信息
     * @param id id
     * @return Role
     */
    Role get(Integer id);

    /**
     * 添加或更新
     * @param roleAddOrUpdateReqVO roleAddOrUpdateReqVO
     * @return Role
     */
    Role addOrUpdate(RoleAddOrUpdateReqVO roleAddOrUpdateReqVO);

    /**
     * 删除角色
     * @param id id
     * @return Boolean
     */
    Boolean del(Integer id);

    List<Role> getByUserId(Integer userId);
}
