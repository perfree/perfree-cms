package com.perfree.system.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserBaseVO {

    @Schema(description = "用户账号")
    @NotBlank(message = "用户账号不能为空")
    private String account;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "网站")
    private String website;
}
