package com.perfree.system.api.role;

import com.perfree.system.api.role.dto.RoleRespDTO;
import com.perfree.system.convert.role.RoleConvert;
import com.perfree.system.model.Role;
import com.perfree.system.service.role.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RoleApiImpl implements RoleApi {
    @Resource
    private RoleService roleService;

    @Override
    public RoleRespDTO getById(Long roleId) {
        Role byId = roleService.getById(roleId);
        return RoleConvert.INSTANCE.convertDto(byId);
    }
}
