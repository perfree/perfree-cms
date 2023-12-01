package com.perfree.commons.enums;

/**
 * @author Perfree
 * @description 定义错误编码枚举
 * @date 15:12 2023/9/28
 */
public enum ErrorCode {
    ACCOUNT_NOT_FOUNT(100000001,"账号不存在!"),
    ACCOUNT_PASSWORD_ERROR(100000002,"账号或密码错误!");

    private final Integer code;

    private final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
