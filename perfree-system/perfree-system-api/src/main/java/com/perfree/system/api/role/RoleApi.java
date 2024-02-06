package com.perfree.system.api.role;

import com.perfree.system.api.role.dto.RoleRespDTO;

import java.util.List;

/**
 * 角色相关
 */
public interface RoleApi {

    /**
     * 根据用户id获取角色列表
     * @param userId 用户id
     * @return List<RoleRespDTO>
     */
    List<RoleRespDTO> getByUserId(Integer userId);

}
