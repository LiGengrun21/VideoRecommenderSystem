package com.lgr.backend.controller;

import com.lgr.backend.model.collection.User;
import com.lgr.backend.service.UserService;
import com.lgr.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Li Gengrun
 * @date 2023/4/18 10:16
 */
@Tag(name="User",description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
//    @Autowired
//    private UserService userService;
//
//
//    @Operation(summary = "添加一个用户",description = "登录后访问")
//    @PostMapping
//    public Result add(@RequestBody User user){
//        return userService.add(user);
//    }
}
