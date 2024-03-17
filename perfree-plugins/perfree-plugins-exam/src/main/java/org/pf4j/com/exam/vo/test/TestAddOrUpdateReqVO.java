package org.pf4j.com.exam.vo.test;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 更新、添加RespVO")
@Data
@EqualsAndHashCode(callSuper = true)
public class TestAddOrUpdateReqVO extends TestBaseVO{

    @Schema(description = "id")
    private String id;

}
