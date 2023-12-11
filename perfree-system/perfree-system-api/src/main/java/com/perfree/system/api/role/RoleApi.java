package com.perfree.system.api.role;

import com.perfree.system.api.role.dto.RoleRespDTO;

/**
 * 角色相关
 */
public interface RoleApi {

    /**
     * 根据角色id获取角色信息
     * @param roleId roleId
     * @return RoleRespDTO
     */
    RoleRespDTO getById(Long roleId);

}
