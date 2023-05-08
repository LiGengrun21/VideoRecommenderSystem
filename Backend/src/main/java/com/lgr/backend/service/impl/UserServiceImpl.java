package com.lgr.backend.service.impl;

import com.lgr.backend.model.collection.User;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.model.request.RegisterRequest;
import com.lgr.backend.repository.UserRepository;
import com.lgr.backend.service.UserService;
import com.lgr.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        //注册前先判断邮箱是否存在
        String newEmail=registerRequest.getEmail();//获取新邮箱
        //将新邮箱传进入，如果返回空，说明新邮箱合法
        if (userRepository.getUserByEmail(newEmail)!=null){
            return Result.FAIL("邮箱已存在，注册用户失败");
        }
        int registerId= userRepository.register(registerRequest);
        User newUser=userRepository.getUserById(registerId);
        if (newUser==null){
            return Result.FAIL("注册用户失败");
        }
        return Result.SUCCESS(newUser);
    }

    @Override
    public Result getUserInfo(int userId) {
        User user=userRepository.getUserById(userId);
        if (user==null){
            return Result.FAIL("找不到用户");
        }
        return Result.SUCCESS(user);
    }

    @Override
    public Result updateProfile(User user) {
        User userResult=userRepository.getUserById(user.getUserId());
        if (userResult==null){
            return Result.FAIL("找不到这个用户，用户ID为"+user.getUserId());
        }
        String newEmail=user.getEmail();//获取新邮箱
        User user1=userRepository.getUserByEmail(newEmail);//根据新邮箱查到的用户
        //如果返回空或者返回的是自己，说明新邮箱合法
        if (user1==null || user1.getUserId()==user.getUserId()){
            userRepository.update(user);
            return Result.SUCCESS(user);
        }
        else {
            return Result.FAIL("邮箱已存在，修改用户信息失败");
        }
    }

    /**
     *   http://localhost:8099/userAvatars/default.jpg 数据库存储图片地址样例
     *   D://WebServer//resources//userAvatars//default.jpg 图像本地存储的地址
     * @param userId
     * @param file
     * @return
     */
    @Override
    public Result uploadUserAvatar(int userId, MultipartFile file) {

        String fileName="user"+userId+".jpg";
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D:\\WebServer\\resources\\userAvatars\\" + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片上传成功后，还要更新数据库，将这个user的图片地址修改为新的地址
        User user=userRepository.getUserById(userId);
        user.setAvatar("http://localhost:8099/userAvatars/"+fileName);
        userRepository.update(user);
        return Result.SUCCESS(user);
    }

    @Override
    public Result getUserAvatarById(int userId) {
        return null;
    }
}
