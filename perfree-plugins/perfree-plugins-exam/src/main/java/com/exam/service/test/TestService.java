package com.exam.service.test;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.vo.test.TestPageReqVO;
import com.exam.model.Test;
import com.exam.vo.test.TestAddOrUpdateReqVO;
import com.perfree.commons.common.PageResult;

import java.util.List;

public interface TestService extends IService<Test> {

    List<Test> test();

    PageResult<Test> testPage(TestPageReqVO pageVO);

    Test addOrUpdate(TestAddOrUpdateReqVO testAddOrUpdateReqVO);

    List<Test> queryByMapperXml();

}
