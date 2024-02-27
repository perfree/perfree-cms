package com.perfree.system.convert.attachConfig;


import com.perfree.commons.common.PageResult;
import com.perfree.system.api.attachConfig.dto.AttachConfigCacheDTO;
import com.perfree.system.model.AttachConfig;
import com.perfree.system.vo.attachConfig.AttachConfigCreateVO;
import com.perfree.system.vo.attachConfig.AttachConfigRespVO;
import com.perfree.system.vo.attachConfig.AttachConfigUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachConfigConvert {
    AttachConfigConvert INSTANCE = Mappers.getMapper(AttachConfigConvert.class);

    List<AttachConfigRespVO> convertRespListVO(List<AttachConfig> attachConfigList);

    AttachConfigRespVO convertRespVO(AttachConfig attachConfig);

    AttachConfig convertCreateVO(AttachConfigCreateVO attachConfigCreateVO);

    AttachConfig convertUpdateVO(AttachConfigUpdateVO attachConfigUpdateVO);

    PageResult<AttachConfigRespVO> convertPageResultVO(PageResult<AttachConfig> attachPage);

    List<AttachConfigCacheDTO> convertCacheListDTO(List<AttachConfig> all);

    AttachConfigCacheDTO convertCacheDTO(AttachConfig attachConfig);

}
