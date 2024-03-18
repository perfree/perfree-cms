package com.exam.mapper;

import com.exam.model.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper{

    List<Test> queryByMapperXml();
}
