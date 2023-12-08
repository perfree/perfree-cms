package com.perfree.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserBaseVO {

    @Schema(description = "用户账号")
    private String account;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "用户登录密码")
    private String password;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "网站")
    private String website;
}
