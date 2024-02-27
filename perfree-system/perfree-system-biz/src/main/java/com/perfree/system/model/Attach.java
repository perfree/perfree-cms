package com.perfree.system.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
@TableName("p_attach")
public class Attach implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 附件名
     */
    private String name;

    /**
     * 附件描述
     */
    @TableField(value = "`desc`")
    private String desc;

    /**
     * 附件路径
     */
    private String path;

    /**
     * 标识
     */
    private String flag;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 存储方式
     */
    private String saveType;


    /**
     * 配置id
     */
    private Integer configId;


    /**
     * 访问地址
     */
    private String url;


    /**
     * 附件分组
     */
    private String attachGroup;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
