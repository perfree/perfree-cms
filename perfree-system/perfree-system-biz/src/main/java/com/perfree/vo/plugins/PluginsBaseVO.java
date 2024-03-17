package com.perfree.vo.plugins;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PluginsBaseVO {

    @Schema(description = "插件名", requiredMode = Schema.RequiredMode.REQUIRED, example = "测试")
    @NotBlank(message = "插件名不能为空")
    private String name;

    @Schema(description = "插件路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "测试")
    @NotBlank(message = "插件路径不能为空")
    private String path;

    @Schema(description = "插件描述")
    private String desc;

    @Schema(description = "插件版本")
    private String version;

    @Schema(description = "插件作者")
    private String author;

    @Schema(description = "插件状态:0禁用,1启用")
    @NotNull(message = "插件状态不能为空")
    private Integer status;
}
