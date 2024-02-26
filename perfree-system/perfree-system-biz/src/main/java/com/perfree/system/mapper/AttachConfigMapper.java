package com.perfree.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.system.model.Attach;
import com.perfree.system.model.AttachConfig;
import com.perfree.system.vo.attachConfig.AttachConfigPageReqVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Mapper
public interface AttachConfigMapper extends BaseMapperX<AttachConfig> {

    default List<AttachConfig> getAll(){
        return selectList(new LambdaQueryWrapper<AttachConfig>().orderByDesc(AttachConfig::getCreateTime));
    }

    default PageResult<AttachConfig> attachConfigPage(AttachConfigPageReqVO pageVO){
        return selectPage(pageVO, new LambdaQueryWrapper<AttachConfig>()
                .like(StringUtils.isNotBlank(pageVO.getName()), AttachConfig::getName, pageVO.getName())
                .orderByDesc(AttachConfig::getCreateTime));
    }

}
