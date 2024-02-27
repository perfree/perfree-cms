package com.perfree.system.vo.option;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 配置RespVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OptionRespVO extends OptionBaseVO{

    @Schema(description = "id" )
    private Integer id;
}
