package com.exam.service.test;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.convert.TestConvert;
import com.exam.mapper.TestMapper;
import com.exam.model.Test;
import com.exam.vo.test.TestAddOrUpdateReqVO;
import com.exam.vo.test.TestPageReqVO;
import com.perfree.commons.common.PageResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService{

    @Resource
    private TestMapper testMapper;

    @Override
    public List<Test> test() {
        return testMapper.selectList();
    }

    @Override
    public PageResult<Test> testPage(TestPageReqVO pageVO) {
        return testMapper.selectPage(pageVO);
    }

    @Override
    public Test addOrUpdate(TestAddOrUpdateReqVO testAddOrUpdateReqVO) {
        Test test = TestConvert.INSTANCE.convertAddOrUpdate(testAddOrUpdateReqVO);
        if (null != test.getId()) {
            testMapper.updateById(test);
        } else {
            testMapper.insert(test);
        }
        return test;
    }

    @Override
    public List<Test> queryByMapperXml() {
        return testMapper.queryByMapperXml();
    }
}
