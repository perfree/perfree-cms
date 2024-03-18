package com.exam.service.test;

import com.exam.mapper.TestMapper;
import com.exam.model.Test;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService{

    @Resource
    private TestMapper testMapper;

    @Override
    public List<Test> queryByMapperXml() {
        return testMapper.queryByMapperXml();
    }
}
