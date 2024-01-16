package com.perfree.system.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Schema(description = "管理后台 - 菜单列表RespVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuRespVO extends MenuBaseVO {
    @Schema(description = "菜单id")
    private String id;

    @Schema(description = "父级id")
    private String parentId;

    @Schema(description = "创建时间")
    private Date createTime;

}
