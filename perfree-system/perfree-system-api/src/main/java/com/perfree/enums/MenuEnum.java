package com.perfree.enums;

import lombok.Getter;

/**
 * @author Perfree
 * @description 菜单枚举
 * @date 15:12 2023/9/28
 */
@Getter
public enum MenuEnum {
    MENU_TYPE_PERMISSION(2,"按钮"),
    ;
    private final Integer code;

    private final String msg;

    MenuEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
