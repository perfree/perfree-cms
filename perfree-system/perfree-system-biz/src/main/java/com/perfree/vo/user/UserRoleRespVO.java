package com.perfree.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Schema(description = "管理后台 - 用户角色RespVO")
@Data
public class UserRoleRespVO {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户角色列表")
    private List<Integer> roles = new ArrayList<>();

}
