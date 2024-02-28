package com.perfree.file.handle.local;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.perfree.commons.utils.FileTypeUtils;
import com.perfree.file.handle.BaseFileHandle;
import com.perfree.system.api.attach.dto.AttachFileDTO;
import com.perfree.system.api.attach.dto.AttachUploadDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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

        String datePath = DateUtil.format(new Date(), "yyyy-MM-dd") + File.separator;
        fileLocalConfig.setBasePath(fileLocalConfig.getBasePath() + datePath);
        if (!FileUtil.exist(fileLocalConfig.getBasePath())) {
            FileUtil.mkdir(fileLocalConfig.getBasePath());
        }

        String fileName = IdUtil.fastSimpleUUID() + "." + FileNameUtil.extName(attachUploadDTO.getFile().getOriginalFilename());
        String mineType = FileTypeUtils.getMineType(attachUploadDTO.getFile().getBytes(), attachUploadDTO.getFile().getOriginalFilename());
        AttachFileDTO attachFileDTO = new AttachFileDTO();
        String originalFilename = attachUploadDTO.getFile().getOriginalFilename();
        attachFileDTO.setType(mineType);
        attachUploadDTO.getFile().transferTo(new File(fileLocalConfig.getBasePath() + fileName));
        attachFileDTO.setName(originalFilename);
        attachFileDTO.setPath( datePath  + fileName);
        attachFileDTO.setDesc(attachUploadDTO.getDesc());
        attachFileDTO.setUrl("/api/attach/file/" + getAttachConfig().getId() + "/get/" + datePath  + fileName);
        attachFileDTO.setConfigId(getAttachConfig().getId());
        attachFileDTO.setStorage(getAttachConfig().getStorage());
        attachFileDTO.setAttachGroup(attachUploadDTO.getAttachGroup());
        return attachFileDTO;
    }

    @Override
    public boolean delete(AttachFileDTO attachFileDTO) {
        FileLocalConfig fileLocalConfig = JSONUtil.toBean(getAttachConfig().getConfig(), FileLocalConfig.class);
        if (FileUtil.exist(fileLocalConfig.getBasePath() + File.separator + attachFileDTO.getPath())) {
            FileUtil.del(fileLocalConfig.getBasePath() + File.separator + attachFileDTO.getPath());
            return true;
        }
        return false;
    }

    @Override
    public byte[] getFileContent(String path) {
        FileLocalConfig fileLocalConfig = JSONUtil.toBean(getAttachConfig().getConfig(), FileLocalConfig.class);
        return FileUtil.readBytes(fileLocalConfig.getBasePath() + File.separator + path);
    }
}
