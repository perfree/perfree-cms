package com.perfree.convert.plugins;


import com.perfree.commons.common.PageResult;
import com.perfree.model.Plugins;
import com.perfree.vo.plugins.PluginsRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PluginsConvert {
    PluginsConvert INSTANCE = Mappers.getMapper(PluginsConvert.class);

    PageResult<PluginsRespVO> convertPageResultVO(PageResult<Plugins> pluginsPageResult);

}
