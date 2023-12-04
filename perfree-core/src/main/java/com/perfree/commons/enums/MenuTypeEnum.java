package com.perfree.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Perfree
 * @description 菜单类型
 * @date 15:11 2023/9/28
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    ADMIN(1), // 后台
    FRONT(0), // 前台
    ;
    private final int type;
}
