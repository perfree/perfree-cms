package com.perfree.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.perfree.cache.CaptchaCacheService;
import com.perfree.commons.common.CommonResult;
import com.perfree.commons.constant.SystemConstants;
import com.perfree.service.menu.MenuService;
import com.perfree.service.user.UserService;
import com.perfree.vo.system.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static com.perfree.commons.common.CommonResult.error;
import static com.perfree.commons.common.CommonResult.success;
import static com.perfree.commons.enums.ErrorCode.CAPTCHA_IMAGE_ERROR;

/**
 * @description 系统基础接口
 * @author Perfree
 * @version 1.0.0
 * @create 2023/9/28 10:16
 **/
@RestController
@Tag(name = "系统基础接口")
@RequestMapping("api")
public class SystemController {

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    @Resource
    private CaptchaCacheService captchaCacheService;


    @PostMapping("login")
    @Operation(summary = "使用账号密码登录")
    public CommonResult<LoginUserRespVO> login(@RequestBody LoginUserReqVO loginUserVO){
        return success(userService.login(loginUserVO));
    }


    @GetMapping("menuList")
    @Operation(summary = "获取当前账号拥有的菜单")
    public CommonResult<List<MenuTreeListRespVO>> menuList(){
        return success(menuService.menuAdminListByLoginUser());
    }

    @GetMapping("userInfo")
    @Operation(summary = "获取当前登录账号的信息")
    public CommonResult<LoginUserInfoRespVO> userInfo(){
        return success(userService.userInfo());
    }

    @PostMapping("logout")
    @Operation(summary = "退出登录")
    public CommonResult<String> logout(){
        return success("退出成功");
    }

    @PostMapping("captchaImage")
    @Operation(summary = "获取验证码")
    public CommonResult<CaptchaImageRespVO> captchaImage(){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(SystemConstants.CAPTCHA_IMAGE_WIDTH, SystemConstants.CAPTCHA_IMAGE_HEIGHT);
        lineCaptcha.setGenerator(new RandomGenerator(SystemConstants.CAPTCHA_RANDOM, SystemConstants.CAPTCHA_LENGTH));
        String code = lineCaptcha.getCode();
        String uuid = IdUtil.simpleUUID();
        captchaCacheService.putCaptcha(uuid, code);
        BufferedImage image = lineCaptcha.getImage();
        CaptchaImageRespVO captchaImageResp = new CaptchaImageRespVO();
        captchaImageResp.setUuid(uuid);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            return error(CAPTCHA_IMAGE_ERROR);
        }
        captchaImageResp.setImg(Base64.encode(os.toByteArray()));
        return success(captchaImageResp);
    }

}
