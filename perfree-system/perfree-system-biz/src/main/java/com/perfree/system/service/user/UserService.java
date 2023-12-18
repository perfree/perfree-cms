package com.perfree.system.service.user;

import com.perfree.system.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.system.vo.system.LoginUserInfoRespVO;
import com.perfree.system.vo.system.LoginUserReqVO;
import com.perfree.system.vo.system.LoginUserRespVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface UserService extends IService<User> {

    /**
     * @author Perfree
     * @description 账号密码登录
     * @date 15:42 2023/9/28
     * @param loginUserVO LoginUserReqVO
     * @return com.perfree.vo.LoginUserRespVO
     */
    LoginUserRespVO login(LoginUserReqVO loginUserVO);

    /**
     * @author Perfree
     * @description 根据账号查询用户信息
     * @date 15:42 2023/9/28
     * @param account 账号
     * @return com.perfree.model.User
     */
   User findByAccount(String account);

    /**
     * 获取当前登录的用户信息
     * @return LoginUserInfoRespVO
     */
    LoginUserInfoRespVO userInfo();

}
