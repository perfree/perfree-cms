package com.perfree.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.system.model.Plugins;
import com.perfree.system.model.Role;
import com.perfree.system.vo.plugins.PluginsPageReqVO;
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
public interface PluginsMapper extends BaseMapperX<Plugins> {

    /**
     * 插件分页
     * @param pageVO pageVO
     * @return PageResult<Plugins>
     */
    default PageResult<Plugins> selectPage(PluginsPageReqVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapper<Plugins>()
                .like(StringUtils.isNotBlank(pageVO.getName()), Plugins::getName, pageVO.getName()));
    }
}
