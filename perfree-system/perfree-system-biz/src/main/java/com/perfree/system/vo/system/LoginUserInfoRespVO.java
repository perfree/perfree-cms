package com.perfree.system.vo.system;

import com.perfree.system.vo.user.UserBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "登录用户信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginUserInfoRespVO extends UserBaseVO {

    @Schema(description = "用户id")
    private Integer id;

    @Schema(description = "角色编码集合")
    private List<String> roles = new ArrayList<>();

    @Schema(description = "权限编码集合")
    private List<String> permissions = new ArrayList<>();
}
