package com.perfree.system.vo.user;

import com.perfree.commons.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "管理后台 - 用户分页ReqVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageReqVO extends PageParam {
    @Schema(description = "用户名")
    private String userName;
}
