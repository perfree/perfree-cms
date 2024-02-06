package com.perfree.enums;

import lombok.Getter;

/**
 * @author Perfree
 * @description 定义错误编码枚举
 * @date 15:12 2023/9/28
 */
@Getter
public enum ErrorCode {
    ACCOUNT_NOT_FOUNT(100000001,"账号不存在!"),
    ACCOUNT_PASSWORD_ERROR(100000002,"账号或密码错误!"),
    CAPTCHA_IMAGE_ERROR(100000004,"验证码生成失败!"),
    CAPTCHA_EXPIRE(100000005,"验证码已过期!"),
    CAPTCHA_VALID_ERROR(100000006,"验证码错误!"),
    MENU_EXISTS_CHILDREN(100000007, "存在子菜单，无法删除!"),
    USER_PASSWORD_NOT_EMPTY(100000008, "密码不能为空!"),
    ACCOUNT_EXIST(100000009, "账户已存在!"),
    ;
    private final Integer code;

    private final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
