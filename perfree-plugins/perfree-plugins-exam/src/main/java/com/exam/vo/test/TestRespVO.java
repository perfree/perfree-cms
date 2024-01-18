package com.exam.vo.test;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 测试RespVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class TestRespVO extends TestBaseVO{

    @Schema(description = "id")
    private String id;

}
