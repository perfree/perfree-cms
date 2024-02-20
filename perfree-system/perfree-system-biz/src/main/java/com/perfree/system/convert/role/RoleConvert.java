package com.perfree.system.convert.role;


import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Role;
import com.perfree.system.vo.role.RoleAddOrUpdateReqVO;
import com.perfree.system.vo.role.RoleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleConvert {
    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleRespVO convertRespVO(Role role);

    PageResult<RoleRespVO> convertPageResultVO(PageResult<Role> rolePageResult);

    Role convertAddOrUpdate(RoleAddOrUpdateReqVO roleAddOrUpdateReqVO);

    List<RoleRespVO> convertRespListVO(List<Role> list);

}
