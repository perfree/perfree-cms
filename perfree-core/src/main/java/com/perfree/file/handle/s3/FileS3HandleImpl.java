package com.perfree.file.handle.s3;

import com.perfree.file.handle.BaseFileHandle;
import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.system.api.attach.dto.AttachUploadDTO;
import io.minio.MinioClient;
import org.springframework.stereotype.Service;

/**
 * 代理文件上传服务(案例)
 */
@Service
public class FileS3HandleImpl extends BaseFileHandle {

    private MinioClient client;

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
