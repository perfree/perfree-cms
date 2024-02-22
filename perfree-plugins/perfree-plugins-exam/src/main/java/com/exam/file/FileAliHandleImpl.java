package com.exam.file;

import com.perfree.file.handle.BaseFileHandle;
import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.system.api.attach.dto.AttachUploadDTO;
import org.springframework.stereotype.Service;

/**
 * 代理文件上传服务(案例)
 */
@Service
public class FileAliHandleImpl implements BaseFileHandle {
    @Override
    public AttachFileDTO upload(AttachUploadDTO attachUploadDTO) {
        System.out.println("上传到阿里云");
        return null;
    }

    @Override
    public boolean delete(AttachFileDTO attachFileDTO) {
        return false;
    }
}
