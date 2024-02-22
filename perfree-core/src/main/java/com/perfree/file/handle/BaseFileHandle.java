package com.perfree.file.handle;

import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.system.api.attach.dto.AttachUploadDTO;

/**
 * TODO:: 定义文件处理器接口,提供给主程序及插件实现,插件可实现该接口即可代理文件上传服务,需支持下拉选择,这点还没想好
 */
public interface BaseFileHandle {

    /**
     * 文件上传
     * @param attachUploadDTO attachUploadDTO
     * @return AttachFileDTO
     */
    AttachFileDTO upload(AttachUploadDTO attachUploadDTO);

    /**
     * 删除文件
     * @param attachFileDTO attachFileDTO
     * @return boolean
     */
    boolean delete(AttachFileDTO attachFileDTO);
}
