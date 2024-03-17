package com.perfree.vo.attachConfig;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 附件配置updateVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AttachConfigUpdateVO extends AttachConfigBaseVO{
    @Schema(description = "id")
    private Integer id;
}
