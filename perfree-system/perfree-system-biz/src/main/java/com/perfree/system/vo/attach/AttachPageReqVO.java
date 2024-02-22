package com.perfree.system.vo.attach;

import com.perfree.commons.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "管理后台 - 附件色分页ReqVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AttachPageReqVO extends PageParam {
    @Schema(description = "附件名")
    private String name;
}
