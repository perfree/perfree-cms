package com.perfree.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private String desc;

    /**
     * 附件路径
     */
    private String path;

    /**
     * 附件后缀
     */
    private String suffix;

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
     * fileKey
     */
    private String fileKey;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
