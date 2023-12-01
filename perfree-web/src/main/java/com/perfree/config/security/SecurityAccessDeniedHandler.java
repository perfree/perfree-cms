package com.perfree.config.security;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.perfree.commons.common.CommonResult;
import com.perfree.commons.enums.ResultCodeEnum;
import com.perfree.commons.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Perfree
 * @description 定义 SecurityAccessDeniedHandler
 * @date 15:37 2023/9/28
 */
@Component
public class SecurityAccessDeniedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        WebUtils.renderString(HttpServletResponse.SC_FORBIDDEN, ContentType.JSON.getValue(), response,
                JSONUtil.toJsonStr(CommonResult.error(ResultCodeEnum.AUTH_FAIL.getCode(), ResultCodeEnum.AUTH_FAIL.getMsg())));
    }
}
