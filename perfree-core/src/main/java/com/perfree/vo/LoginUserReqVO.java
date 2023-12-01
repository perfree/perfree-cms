package com.perfree.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Perfree
 * @description 账号密码登录  Request VO
 * @date 15:36 2023/9/28
 */
@Schema(description = "账号密码登录 Request VO")
@Data
public class LoginUserReqVO {

    @Schema(description = "账号", example = "admin")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 1, max = 16, message = "账号长度为 1-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String account;

    @Schema(description = "密码", example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

}
