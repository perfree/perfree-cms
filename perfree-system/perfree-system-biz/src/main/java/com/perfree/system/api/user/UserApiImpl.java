package com.perfree.system.api.user;

import com.perfree.system.api.user.dto.UserRespDTO;
import com.perfree.system.convert.user.UserConvert;
import com.perfree.system.model.User;
import com.perfree.system.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserApiImpl implements UserApi{

    @Resource
    private UserService userService;

    @Override
    public UserRespDTO findByAccount(String account) {
        User byAccount = userService.findByAccount(account);
        return UserConvert.INSTANCE.convertDto(byAccount);
    }
}
