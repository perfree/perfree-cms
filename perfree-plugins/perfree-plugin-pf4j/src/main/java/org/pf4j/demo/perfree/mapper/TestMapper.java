package org.pf4j.demo.perfree.mapper;

import com.perfree.commons.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.pf4j.demo.perfree.model.Test;

import java.util.List;

@Mapper
public interface TestMapper extends BaseMapperX<Test> {

}
