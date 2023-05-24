package com.lgr.backend.controller;

import com.lgr.backend.model.collection.Admin;
import com.lgr.backend.model.request.AdminRegisterRequest;
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
    @PostMapping()
    public Result addAdmin(AdminRegisterRequest admin){
        return adminService.add(admin);
    }

    @Operation(summary = "删除一个管理员",description = "逻辑删除，不是物理删除")
    @ResponseBody
    @DeleteMapping("/logic")
    public Result deleteAdmin(@RequestParam("adminId") int adminId){
        //System.out.println("controller:"+adminId);
        return adminService.LogicDeleteById(adminId);
    }

    @Operation(summary = "恢复一个被删除的管理员")
    @ResponseBody
    @DeleteMapping("/recover")
    public Result recoverAdmin(@RequestParam("adminId") int adminId){
        return adminService.recoverAdminById(adminId);
    }

    /**
     *
     * @param admin
     * @return 新的管理员信息
     */
    @Operation(summary = "修改管理员信息")
    @ResponseBody
    @PutMapping()
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

    @Operation(summary = "控制台获取管理员人数")
    @ResponseBody
    @GetMapping("/number/admin")
    public Result getAdminNumber(){
        return adminService.getAdminNumber();
    }

    @Operation(summary = "控制台获取用户人数")
    @ResponseBody
    @GetMapping("/number/user")
    public Result getUserNumber(){
        return adminService.getUserNumber();
    }

    @Operation(summary = "控制台获取电影数")
    @ResponseBody
    @GetMapping("/number/movie")
    public Result getMovieNumber(){
        return adminService.getMovieNumber();
    }

    @Operation(summary = "控制台获取评价数")
    @ResponseBody
    @GetMapping("/number/rating")
    public Result getRatingNumber(){
        return adminService.getRatingNumber();
    }

    @Operation(summary = "管理员获取20个最多评价的电影")
    @ResponseBody
    @GetMapping("/mostRated")
    public Result getMovieMostViewedData(){
        return adminService.getMovieMostViewedData();
    }

    @Operation(summary = "管理员获取电影评分在各个区间的数量")
    @ResponseBody
    @GetMapping("/topRated")
    public Result getMovieTopRatedData(){
        return adminService.getMovieTopRatedData();
    }

    @Operation(summary = "管理员列表",description="包括被删除的管理员")
    @ResponseBody
    @GetMapping("/list")
    public Result getAdminList(){
        return adminService.getAdminList();
    }
}
