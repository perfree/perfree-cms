package com.exam.vo.test;

import com.perfree.commons.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "管理后台 - 测试分页ReqVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class TestPageReqVO extends PageParam {
    @Schema(description = "名称")
    private String name;
}
