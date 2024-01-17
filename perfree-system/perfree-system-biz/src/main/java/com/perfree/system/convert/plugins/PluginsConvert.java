package com.perfree.system.convert.plugins;


import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Plugins;
import com.perfree.system.vo.plugins.PluginsRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PluginsConvert {
    PluginsConvert INSTANCE = Mappers.getMapper(PluginsConvert.class);

    PageResult<PluginsRespVO> convertPageResultVO(PageResult<Plugins> pluginsPageResult);

}
