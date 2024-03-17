package org.pf4j.com.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.pf4j.com.exam.model.Test;
import org.pf4j.com.exam.vo.test.TestPageReqVO;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.mapper.BaseMapperX;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper extends BaseMapperX<Test> {

    default PageResult<Test> selectPage(TestPageReqVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapper<Test>()
                .like(StringUtils.isNotBlank(pageVO.getName()), Test::getName, pageVO.getName()));
    }

    List<Test> queryByMapperXml();
}
