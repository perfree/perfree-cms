package com.perfree.convert.role;


import com.perfree.commons.common.PageResult;
import com.perfree.model.Role;
import com.perfree.vo.role.RoleAddOrUpdateReqVO;
import com.perfree.vo.role.RoleRespVO;
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
