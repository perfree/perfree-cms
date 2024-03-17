package org.pf4j.demo.perfree.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.pf4j.demo.perfree.model.Test;
import org.pf4j.demo.perfree.vo.test.TestRespVO;

@Mapper(componentModel = "spring")
public interface TestConvert {
    TestConvert INSTANCE = Mappers.getMapper(TestConvert.class);


    TestRespVO convertRespVO(Test test);
}
