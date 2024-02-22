package com.perfree.system.api.attach.dto;

import lombok.Data;

@Data
public class AttachFileDTO {

    /**
     * 附件名
     */
    private String name;

    /**
     * 附件描述
     */
    private String desc;

    /**
     * 附件路径
     */
    private String path;

    /**
     * 附件后缀
     */
    private String suffix;

    /**
     * 标识
     */
    private String flag;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 存储方式
     */
    private String saveType;

    /**
     * fileKey
     */
    private String fileKey;
}
