package com.perfree.file.handle;

import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.system.api.attach.dto.AttachUploadDTO;
import org.springframework.stereotype.Service;

/**
 * 上传文件到本地的处理逻辑
 */
@Service
public class FileLocalHandleImpl implements BaseFileHandle{

    @Override
    public AttachFileDTO upload(AttachUploadDTO attachUploadDTO) {
        System.out.println("上传到本地");
        return null;
    }

    @Override
    public boolean delete(AttachFileDTO attachFileDTO) {
        return false;
    }

}
