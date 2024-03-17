package org.pf4j.demo.perfree.service.test;

import jakarta.annotation.Resource;
import org.pf4j.demo.perfree.mapper.TestMapper;
import org.pf4j.demo.perfree.model.Test;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService{

    @Resource
    private TestMapper testMapper;
}
