package com.perfree.system.service.user;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.cache.CaptchaCacheService;
import com.perfree.commons.common.PageResult;
import com.perfree.commons.exception.ServiceException;
import com.perfree.enums.ErrorCode;
import com.perfree.security.SecurityConstants;
import com.perfree.security.SecurityFrameworkUtils;
import com.perfree.security.util.JwtUtil;
import com.perfree.security.vo.LoginUserVO;
import com.perfree.system.convert.user.UserConvert;
import com.perfree.system.mapper.RoleMapper;
import com.perfree.system.mapper.UserMapper;
import com.perfree.system.mapper.UserRoleMapper;
import com.perfree.system.model.Role;
import com.perfree.system.model.User;
import com.perfree.system.model.UserRole;
import com.perfree.system.vo.system.LoginUserInfoRespVO;
import com.perfree.system.vo.system.LoginUserReqVO;
import com.perfree.system.vo.system.LoginUserRespVO;
import com.perfree.system.vo.user.*;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.perfree.enums.ErrorCode.ACCOUNT_EXIST;
import static com.perfree.enums.ErrorCode.USER_PASSWORD_NOT_EMPTY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private CaptchaCacheService captchaCacheService;

    @Override
    public LoginUserRespVO login(LoginUserReqVO loginUserVO) {
        String captcha = captchaCacheService.getCaptcha(loginUserVO.getUuid());
        if (StringUtils.isBlank(captcha)){
            throw new ServiceException(ErrorCode.CAPTCHA_EXPIRE);
        }
        captchaCacheService.removeCaptcha(loginUserVO.getUuid());
        if (!captcha.equals(loginUserVO.getCode())) {
            throw new ServiceException(ErrorCode.CAPTCHA_VALID_ERROR);
        }
        User user = userMapper.findByAccount(loginUserVO.getUsername());
        if (null == user) {
            throw new ServiceException(ErrorCode.ACCOUNT_NOT_FOUNT);
        }
        // 校验密码,PS: 为了兼容老数据,这里依然采用MD5 + Salt的方式
        String hexPassword = DigestUtil.md5Hex(user.getSalt() + loginUserVO.getPassword());
        if (!hexPassword.equals(user.getPassword())) {
            throw new ServiceException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
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

    @Override
    public LoginUserInfoRespVO userInfo() {
        LoginUserVO loginUser = SecurityFrameworkUtils.getLoginUser();
        assert loginUser != null;
        User user = userMapper.selectById(loginUser.getId());
        user.setPassword(null);
        user.setSalt(null);
        LoginUserInfoRespVO loginUserInfoRespVO = UserConvert.INSTANCE.convertLoginInfo(user);
        List<Role> roleList = roleMapper.getByUserId(loginUser.getId());
        for (Role role : roleList) {
            loginUserInfoRespVO.getRoles().add(role.getCode());
        }
        return loginUserInfoRespVO;
    }

    @Override
    public PageResult<User> userPage(UserPageReqVO pageVO) {
        return userMapper.selectPage(pageVO);
    }

    @Override
    public User get(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional
    public Boolean del(Integer id) {
        userRoleMapper.deleteByUserId(id);
        userMapper.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public User addUser(UserAddReqVO userAddReqVO) {
        User user = UserConvert.INSTANCE.convertAddVO(userAddReqVO);
        if (StringUtils.isBlank(userAddReqVO.getPassword())) {
            throw new ServiceException(USER_PASSWORD_NOT_EMPTY);
        }
        User byAccount = userMapper.findByAccount(userAddReqVO.getAccount());
        if (null != byAccount) {
            throw new ServiceException(ACCOUNT_EXIST);
        }
        user.setSalt(IdUtil.simpleUUID());
        // PS: 为了兼容老数据,这里依然采用MD5 + Salt的方式
        String hexPassword = DigestUtil.md5Hex(user.getSalt() + userAddReqVO.getPassword());
        user.setPassword(hexPassword);
        userMapper.insert(user);
        return null;
    }

    @Override
    @Transactional
    public User updateUser(UserUpdateReqVO userUpdateReqVO) {
        User user = UserConvert.INSTANCE.convertUpdateVO(userUpdateReqVO);
        User byAccount = userMapper.findByAccount(userUpdateReqVO.getAccount());
        if (null != byAccount && !byAccount.getId().equals(user.getId())) {
            throw new ServiceException(ACCOUNT_EXIST);
        }
        userMapper.updateById(user);
        return user;
    }

    @Override
    public UserRoleRespVO getUserRole(Integer id) {
        UserRoleRespVO userRoleRespVO = new UserRoleRespVO();
        List<UserRole> userRoleList = userRoleMapper.getByUserId(id);
        userRoleRespVO.setId(id);
        for (UserRole userRole : userRoleList) {
            userRoleRespVO.getRoles().add(userRole.getRoleId());
        }
        return userRoleRespVO;
    }

    @Override
    @Transactional
    public Boolean updateUserRole(UserRoleReqVO userRoleReqVO) {
        userRoleMapper.deleteByUserId(userRoleReqVO.getId());
        List<UserRole> userRoleList = new ArrayList<>();
        for (Integer role : userRoleReqVO.getRoles()) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(role);
            userRole.setUserId(userRoleReqVO.getId());
            userRoleList.add(userRole);
        }
        userRoleMapper.insertBatch(userRoleList);
        return true;
    }

    @Override
    public Boolean resetPassword(UserResetPasswordReqVO resetPasswordReqVO) {
        if (StringUtils.isBlank(resetPasswordReqVO.getPassword())) {
            throw new ServiceException(USER_PASSWORD_NOT_EMPTY);
        }
        User user = userMapper.selectById(resetPasswordReqVO.getId());
        // PS: 为了兼容老数据,这里依然采用MD5 + Salt的方式
        String hexPassword = DigestUtil.md5Hex(user.getSalt() + resetPasswordReqVO.getPassword());
        user.setPassword(hexPassword);
        userMapper.updateById(user);
        return true;
    }
}
