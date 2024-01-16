package com.perfree.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.commons.utils.MyBatisUtils;
import com.perfree.system.model.Role;
import com.perfree.system.vo.role.RolePageReqVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Mapper
public interface RoleMapper extends BaseMapperX<Role> {

    default PageResult<Role> selectPage(RolePageReqVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(pageVO.getName()), Role::getName, pageVO.getName()));
    }

}
