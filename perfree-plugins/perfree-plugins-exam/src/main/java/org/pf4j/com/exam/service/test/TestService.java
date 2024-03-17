package org.pf4j.com.exam.service.test;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pf4j.com.exam.model.Test;
import org.pf4j.com.exam.vo.test.TestAddOrUpdateReqVO;
import org.pf4j.com.exam.vo.test.TestPageReqVO;
import com.perfree.commons.common.PageResult;

import java.util.List;

public interface TestService extends IService<Test> {

    List<Test> test();

    PageResult<Test> testPage(TestPageReqVO pageVO);

    Test addOrUpdate(TestAddOrUpdateReqVO testAddOrUpdateReqVO);

    List<Test> queryByMapperXml();

}
