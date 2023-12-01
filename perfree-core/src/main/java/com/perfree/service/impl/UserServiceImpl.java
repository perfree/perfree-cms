package com.perfree.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.commons.constant.SecurityConstants;
import com.perfree.commons.exception.ServiceException;
import com.perfree.commons.utils.JwtUtil;
import com.perfree.mapper.UserMapper;
import com.perfree.model.User;
import com.perfree.service.IUserService;
import com.perfree.vo.LoginUserReqVO;
import com.perfree.vo.LoginUserRespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

import static com.perfree.commons.enums.ErrorCode.ACCOUNT_NOT_FOUNT;
import static com.perfree.commons.enums.ErrorCode.ACCOUNT_PASSWORD_ERROR;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public LoginUserRespVO login(LoginUserReqVO loginUserVO) {
        User user = userMapper.findByAccount(loginUserVO.getAccount());
        if (null == user) {
            throw new ServiceException(ACCOUNT_NOT_FOUNT);
        }
        // 校验密码,PS: 为了兼容老数据,这里依然采用MD5 + Salt的方式
        String hexPassword = DigestUtil.md5Hex(user.getSalt() + loginUserVO.getPassword());
        if (!hexPassword.equals(user.getPassword())) {
            throw new ServiceException(ACCOUNT_PASSWORD_ERROR);
        }
        // 生成Token
        String token = JwtUtil.generateToken(user.getAccount(), false);
        Date expirationDate = new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_REMEMBER_TIME * 1000);
        // 生成refreshToken
        String refreshToken = JwtUtil.getRefreshToken(user.getAccount(), expirationDate);
        // 组装返回信息
        LoginUserRespVO loginUserRespVO = new LoginUserRespVO();
        loginUserRespVO.setUserId(user.getId());
        loginUserRespVO.setAccessToken(token);
        loginUserRespVO.setRefreshToken(refreshToken);
        loginUserRespVO.setExpiresTime(expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return loginUserRespVO;
    }

    @Override
    public User findByAccount(String account) {
        return userMapper.findByAccount(account);
    }
}
