package com.exam.vo.test;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 测试RespVO")
public class TestRespVO extends TestBaseVO{

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
