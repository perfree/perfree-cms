package com.perfree.system.vo.system;


import com.perfree.system.vo.menu.MenuBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单 Response VO")
@Data
public class MenuTreeListRespVO extends MenuBaseVO {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "父级ID")
    private String parentId;

    @Schema(description = "子菜单")
    private List<MenuTreeListRespVO> children;
}
