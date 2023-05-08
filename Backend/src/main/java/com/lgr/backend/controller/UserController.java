package com.lgr.backend.controller;

import com.lgr.backend.model.collection.User;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.model.request.RegisterRequest;
import com.lgr.backend.service.UserService;
import com.lgr.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Li Gengrun
 * @date 2023/4/18 10:16
 */
@Tag(name="User",description = "用户模块")
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @ResponseBody
    @GetMapping("/login")
    public Result userLogin(LoginRequest loginRequest){
        System.out.println("接收到的数据:"+loginRequest.getEmail()+loginRequest.getPassword());
        return userService.login(loginRequest);
    }

    @Operation(summary = "用户注册")
    @ResponseBody
    @PostMapping("/register")
    public Result userRegister(RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

    @Operation(summary = "显示用户个人信息")
    @ResponseBody
    @GetMapping("/profile")
    public Result showUserProfile(@RequestParam("userId") int userId){
        return userService.getUserInfo(userId);
    }

    @Operation(summary = "修改用户个人信息",description = "本接口不修改图片，文件上传使用单独的接口")
    @ResponseBody
    @PutMapping("/profile")
    public Result updateUserProfile(User user){
//        System.out.println(user.getUserId());
//        System.out.println(user.getEmail());
//        System.out.println(user.getUsername());
        return userService.updateProfile(user);
    }

    @Operation(summary = "上传用户头像",description = "从本地选择文件，上传到本地的特定位置，即：存储在本地的nginx静态资源服务器")
    @ResponseBody
    @PostMapping(value ="/profile/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result updateUserAvatar(@RequestParam("userId") int userId, @RequestParam("userAvatar") MultipartFile file){
        return userService.uploadUserAvatar(userId,file);
    }

//    @Operation(summary = "根据userId获取头像地址")
//    @ResponseBody
//    @GetMapping("/userAvatar")
//    public Result getUserAvatarById(int userId){
//        return userService.getUserAvatarById(userId);
//    }




//    @Operation(summary = "添加一个用户",description = "登录后访问")
//    @PostMapping
//    public Result add(@RequestBody User user){
//        return userService.add(user);
//    }
}
