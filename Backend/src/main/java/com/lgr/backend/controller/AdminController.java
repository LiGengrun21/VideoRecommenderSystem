package com.lgr.backend.controller;

import com.lgr.backend.model.collection.Admin;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.service.AdminService;
import com.lgr.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Li Gengrun
 * @date 2023/4/18 12:36
 */
@Tag(name="Admin",description = "管理员模块")
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "根据id查询管理员",description = "不考虑逻辑删除")
    @ResponseBody
    @GetMapping("/adminId")
    public Result getAdminById(int adminId){
        return adminService.findAdminById(adminId);
    }

    @Operation(summary = "根据id查询存在的管理员",description = "考虑逻辑删除")
    @ResponseBody
    @GetMapping("/existed/adminId")
    public Result getExistedAdminById(int adminId){
        return adminService.findExistedAdminById(adminId);
    }

    @Operation(summary = "添加一个管理员")
    @ResponseBody
    @PostMapping("/adminId")
    public Result addAdmin(Admin admin){
        return adminService.add(admin);
    }

    @Operation(summary = "删除一个管理员",description = "逻辑删除，不是物理删除")
    @ResponseBody
    @DeleteMapping("/adminId/logic")
    public Result deleteAdmin(int adminId){
        return adminService.LogicDeleteById(adminId);
    }

    /**
     *
     * @param admin
     * @return 新的管理员信息
     */
    @Operation(summary = "修改管理员信息")
    @ResponseBody
    @PutMapping("/adminId")
    public Result updateAdmin(Admin admin){
        return adminService.update(admin);
    }

    @Operation(summary = "管理员登录")
    @ResponseBody
    @GetMapping("/login")
    public Result adminLogin(LoginRequest loginRequest){
        return adminService.login(loginRequest);
    }

    @Operation(summary = "上传管理员头像")
    @ResponseBody
    @PostMapping(value ="/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadAdminAvatar(@RequestParam("adminId") int adminId, @RequestParam("adminAvatar") MultipartFile file){
        return adminService.uploadAdminAvatar(adminId, file);
    }
}
