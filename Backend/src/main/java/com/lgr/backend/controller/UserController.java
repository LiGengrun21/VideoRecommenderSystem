package com.lgr.backend.controller;

import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.model.request.RegisterRequest;
import com.lgr.backend.service.UserService;
import com.lgr.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li Gengrun
 * @date 2023/4/18 10:16
 */
@Tag(name="User",description = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @ResponseBody
    @GetMapping("/login")
    public Result userLogin(LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

    @Operation(summary = "用户注册")
    @ResponseBody
    @PostMapping("/register")
    public Result userRegister(RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }




//    @Operation(summary = "添加一个用户",description = "登录后访问")
//    @PostMapping
//    public Result add(@RequestBody User user){
//        return userService.add(user);
//    }
}
