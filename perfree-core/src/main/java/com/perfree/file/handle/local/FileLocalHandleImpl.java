package com.perfree.file.handle.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.perfree.file.handle.BaseFileHandle;
import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.system.api.attach.dto.AttachUploadDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * 上传文件到本地的处理逻辑
 */
@Service
public class FileLocalHandleImpl extends BaseFileHandle {

    @Override
    public AttachFileDTO upload(AttachUploadDTO attachUploadDTO) throws IOException {
        FileLocalConfig fileLocalConfig = JSONUtil.toBean(getAttachConfig().getConfig(), FileLocalConfig.class);
        if (!fileLocalConfig.getBasePath().endsWith(File.separator)) {
            fileLocalConfig.setBasePath(fileLocalConfig.getBasePath() + File.separator);
        }

        if (!FileUtil.exist(fileLocalConfig.getBasePath())) {
            FileUtil.mkdir(fileLocalConfig.getBasePath());
        }

        String originalFilename = attachUploadDTO.getFile().getOriginalFilename();
        attachUploadDTO.getFile().transferTo(new File(fileLocalConfig.getBasePath() + originalFilename));
        AttachFileDTO attachFileDTO = new AttachFileDTO();
        attachFileDTO.setName(originalFilename);
        attachFileDTO.setPath(fileLocalConfig.getBasePath() + originalFilename);
        attachFileDTO.setDesc(attachUploadDTO.getDesc());
        attachFileDTO.setUrl("/" + getAttachConfig().getId() + "/get/" + attachFileDTO.getName());
        attachFileDTO.setConfigId(getAttachConfig().getId());
        attachFileDTO.setAttachGroup(attachUploadDTO.getAttachGroup());
        System.out.println(attachFileDTO);
        System.out.println("上传到本地");
        return attachFileDTO;
    }

    @Override
    public boolean delete(AttachFileDTO attachFileDTO) {
        return false;
    }

    @Override
    public byte[] getFileContent(String path) {
        FileLocalConfig fileLocalConfig = JSONUtil.toBean(getAttachConfig().getConfig(), FileLocalConfig.class);
        return FileUtil.readBytes(fileLocalConfig.getBasePath() + File.separator + path);
    }
}
