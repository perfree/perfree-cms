package com.perfree.system.convert.role;


import com.perfree.system.api.role.dto.RoleRespDTO;
import com.perfree.system.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleConvert {
    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleRespDTO convertDto(Role byId);

}
