package com.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.model.Test;
import com.exam.vo.test.TestPageReqVO;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.mapper.BaseMapperX;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends BaseMapperX<Test> {

    default PageResult<Test> selectPage(TestPageReqVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapper<Test>()
                .like(StringUtils.isNotBlank(pageVO.getName()), Test::getName, pageVO.getName()));
    }
}
