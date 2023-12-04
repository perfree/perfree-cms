package com.perfree.commons.enums;

import lombok.Getter;

/**
 * @author Perfree
 * @description 定义响应编码枚举
 * @date 15:11 2023/9/28
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"成功"),
    FAIL(500,"失败"),
    AUTH_FAIL(403,"认证失败");

    private final Integer code;

    private final String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
