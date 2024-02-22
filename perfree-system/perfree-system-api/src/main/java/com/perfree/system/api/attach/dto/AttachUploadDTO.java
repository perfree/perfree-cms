package com.perfree.system.api.attach.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AttachUploadDTO {

    private MultipartFile file;

    private String desc;
}
