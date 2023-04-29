package com.lgr.backend.service.impl;

import com.lgr.backend.model.collection.User;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.model.request.RegisterRequest;
import com.lgr.backend.repository.UserRepository;
import com.lgr.backend.service.UserService;
import com.lgr.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Li Gengrun
 * @date 2023/4/13 17:51
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Result login(LoginRequest loginRequest) {
        User user= userRepository.login(loginRequest);
        if (user==null){
            return Result.FAIL("邮箱地址或密码错误");
        }
        return Result.SUCCESS(user);
    }

    @Override
    public Result register(RegisterRequest registerRequest) {
        int registerId= userRepository.register(registerRequest);
        User newUser=userRepository.getUserById(registerId);
        if (newUser==null){
            return Result.FAIL("注册用户失败");
        }
        return Result.SUCCESS(newUser);
    }
}
