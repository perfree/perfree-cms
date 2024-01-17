package com.perfree.system.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Schema(description = "管理后台 - 角色RespVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleAddOrUpdateReqVO extends RoleBaseVO{
    @Schema(description = "id")
    private Integer id;
}
