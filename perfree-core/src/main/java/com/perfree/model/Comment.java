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
@TableName("p_comment")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 父级id
     */
    private Integer pid;

    /**
     * 顶层父级id
     */
    private Integer topPid;

    /**
     * 用户iD
     */
    private Integer userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 状态:0正常,1:待审核
     */
    private Integer status;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 网站地址
     */
    private String website;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 评论人
     */
    private String userName;

    /**
     * 设备类型
     */
    private String device;

    /**
     * ip
     */
    private String ip;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
