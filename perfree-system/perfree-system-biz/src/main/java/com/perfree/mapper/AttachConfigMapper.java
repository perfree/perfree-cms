package com.perfree.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.model.AttachConfig;
import com.perfree.vo.attachConfig.AttachConfigPageReqVO;
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
        return selectList(new LambdaQueryWrapper<AttachConfig>().orderByDesc(AttachConfig::getMaster).orderByDesc(AttachConfig::getCreateTime));
    }

    default PageResult<AttachConfig> attachConfigPage(AttachConfigPageReqVO pageVO){
        return selectPage(pageVO, new LambdaQueryWrapper<AttachConfig>()
                .like(StringUtils.isNotBlank(pageVO.getName()), AttachConfig::getName, pageVO.getName())
                .orderByDesc(AttachConfig::getCreateTime));
    }

    default void clearMaster() {
        update(new AttachConfig(), new LambdaUpdateWrapper<AttachConfig>()
                .eq(AttachConfig::getMaster, true)
                .set(AttachConfig::getMaster, false)
        );
    }

    default void updateMaster(Integer id){
        update(new AttachConfig(), new LambdaUpdateWrapper<AttachConfig>()
                .eq(AttachConfig::getId, id)
                .set(AttachConfig::getMaster, true)
        );
    }

}
