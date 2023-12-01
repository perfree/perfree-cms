package com.perfree.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@TableName("p_user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户登录密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 网站
     */
    private String website;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
