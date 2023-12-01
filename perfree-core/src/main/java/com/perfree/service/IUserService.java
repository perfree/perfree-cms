package com.perfree.service;

import com.perfree.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.vo.LoginUserReqVO;
import com.perfree.vo.LoginUserRespVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface IUserService extends IService<User> {

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
}
