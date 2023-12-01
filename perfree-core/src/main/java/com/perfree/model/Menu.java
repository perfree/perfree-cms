package com.perfree.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Getter
@Setter
@TableName("p_menu")
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private String pid;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单链接
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序序号
     */
    private Integer seq;

    /**
     * 菜单类型0:前台,1:后台
     */
    private Integer type;

    /**
     * 菜单打开方式:0本页,1:新窗口
     */
    private Integer target;

    /**
     * 菜单状态0:启用,1禁用
     */
    private Integer status;

    /**
     * 插件id
     */
    private String pluginId;

    /**
     * 菜单标识:0:系统自带,1:用户创建,2:插件
     */
    private Integer flag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
