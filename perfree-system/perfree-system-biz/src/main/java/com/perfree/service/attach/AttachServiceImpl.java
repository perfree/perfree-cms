package com.perfree.service.attach;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.exception.ServiceException;
import com.perfree.convert.attach.AttachConvert;
import com.perfree.enums.ErrorCode;
import com.perfree.file.FileHandleService;
import com.perfree.file.handle.BaseFileHandle;
import com.perfree.mapper.AttachMapper;
import com.perfree.model.Attach;
import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.vo.attach.AttachPageReqVO;
import com.perfree.vo.attach.AttachUpdateVO;
import com.perfree.vo.attach.AttachUploadVO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
public class AttachServiceImpl extends ServiceImpl<AttachMapper, Attach> implements AttachService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachServiceImpl.class);

    @Resource
    private AttachMapper attachMapper;

    @Resource
    private FileHandleService fileHandleService;


    @Override
    public PageResult<Attach> attachPage(AttachPageReqVO pageVO) {
        return attachMapper.selectPage(pageVO);
    }

    @Override
    @Transactional
    public Attach create(AttachUploadVO attachUploadVO) {
        try{
            BaseFileHandle fileHandle = fileHandleService.getFileHandle(attachUploadVO.getAttachConfigId());
            AttachFileDTO upload = fileHandle.upload(AttachConvert.INSTANCE.convertAttachUploadDTO(attachUploadVO));
            Attach attach = AttachConvert.INSTANCE.convertAttachFileDTO(upload);
            if (null == attach) {
                LOGGER.error("file upload error, Attach is empty");
                throw new ServiceException(ErrorCode.FILE_HANDLE_ERROR);
            }
            attachMapper.insert(attach);
            return attach;
        }catch (Exception e) {
            LOGGER.error("file upload error", e);
            throw new ServiceException(ErrorCode.FILE_HANDLE_ERROR);
        }

    }

    @Override
    @Transactional
    public Boolean del(Integer id) {
        Attach attach = attachMapper.selectById(id);
        BaseFileHandle fileHandle = fileHandleService.getFileHandle(attach.getConfigId());
        boolean result = fileHandle.delete(AttachConvert.INSTANCE.convertToAttachFileDTO(attach));
        if (!result) {
            return false;
        }
        attachMapper.deleteById(id);
        return true;
    }

    @Override
    public byte[] getFileContent(Integer configId, String path) {
        return fileHandleService.getFileContent(configId, path);
    }

    @Override
    public List<Attach> getAllAttachGroup() {
        return attachMapper.getAllAttachGroup();
    }

    @Override
    @Transactional
    public Boolean updateAttach(AttachUpdateVO attachUpdateVO) {
        Attach attach = AttachConvert.INSTANCE.convertByUpdateVO(attachUpdateVO);
        attachMapper.updateById(attach);
        return true;
    }
}
