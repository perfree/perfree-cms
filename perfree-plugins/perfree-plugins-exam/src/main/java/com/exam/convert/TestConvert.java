package com.exam.convert;

import com.exam.vo.test.TestRespVO;
import com.exam.model.Test;
import com.exam.vo.test.TestAddOrUpdateReqVO;
import com.perfree.commons.common.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TestConvert {
    TestConvert INSTANCE = Mappers.getMapper(TestConvert.class);

    List<TestRespVO> convertRespListVO(List<Test> test);

    TestRespVO convertRespVO(Test test);


    PageResult<TestRespVO> convertPageResultVO(PageResult<Test> testPageResult);

    Test convertAddOrUpdate(TestAddOrUpdateReqVO testAddOrUpdateReqVO);

    TestRespVO convertTestVO(TestAddOrUpdateReqVO testAddOrUpdateReqVO);
}
