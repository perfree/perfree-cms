package com.perfree.system.convert.attach;


import com.perfree.commons.common.PageResult;
import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.system.api.attach.dto.AttachUploadDTO;
import com.perfree.system.model.Attach;
import com.perfree.system.vo.attach.AttachGroupRespVO;
import com.perfree.system.vo.attach.AttachRespVO;
import com.perfree.system.vo.attach.AttachUpdateVO;
import com.perfree.system.vo.attach.AttachUploadVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachConvert {
    AttachConvert INSTANCE = Mappers.getMapper(AttachConvert.class);

    PageResult<AttachRespVO> convertPageResultVO(PageResult<Attach> rolePageResult);

    AttachUploadDTO convertAttachUploadDTO(AttachUploadVO attachUploadVO);

    Attach convertAttachFileDTO(AttachFileDTO upload);

    AttachRespVO convertRespVO(Attach attach);

    AttachFileDTO convertToAttachFileDTO(Attach attach);

    List<AttachGroupRespVO> convertGroupRespVO(List<Attach> attachList);

    Attach convertByUpdateVO(AttachUpdateVO attachUpdateVO);

}
