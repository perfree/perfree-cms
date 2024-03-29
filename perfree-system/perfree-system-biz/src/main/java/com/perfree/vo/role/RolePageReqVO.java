package com.perfree.vo.role;

import com.perfree.commons.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "管理后台 - 角色分页ReqVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageReqVO extends PageParam {
    @Schema(description = "角色名")
    private String name;
}
