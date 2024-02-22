package com.perfree.system.vo.attach;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AttachBaseVO {

    @Schema(description = "附件名")
    private String name;

    @Schema(description = "附件描述")
    private String desc;

    @Schema(description = "附件路径")
    private String path;

    @Schema(description = "附件后缀")
    private String suffix;

    @Schema(description = "附件标识")
    private String flag;

    @Schema(description = "文件类型")
    private String type;

    @Schema(description = "存储方式")
    private String saveType;

    @Schema(description = "fileKey")
    private String fileKey;
}
