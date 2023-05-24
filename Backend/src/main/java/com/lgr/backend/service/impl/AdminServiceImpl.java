package com.lgr.backend.service.impl;

import com.lgr.backend.model.collection.Admin;
import com.lgr.backend.model.collection.User;
import com.lgr.backend.model.request.AdminRegisterRequest;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.repository.AdminRepository;
import com.lgr.backend.service.AdminService;
import com.lgr.backend.util.Result;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.spark.sql.fieldTypes.api.java.ObjectId;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Gengrun
 * @date 2023/4/13 17:52
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Result add(AdminRegisterRequest admin) {
//        //原集合里没有这个ID，可以插入
//       if (adminRepository.getAdminById(admin.getAdminId())==null){
//           return Result.SUCCESS(adminRepository.add(admin));
//       }
//       return Result.FAIL("集合里已经存在"+admin.getAdminId());

        //注册前先判断邮箱是否存在
        String newEmail=admin.getEmail();
        //将新邮箱传进入，如果返回空，说明新邮箱合法
        if (adminRepository.getAdminByEmail(newEmail)!=null){
            return Result.FAIL("邮箱已存在，注册管理员失败");
        }
        int registerId= adminRepository.add(admin);
        Admin newAdmin=adminRepository.getAdminById(registerId);
        if (newAdmin==null){
            return Result.FAIL("注册管理员失败");
        }
        return Result.SUCCESS(newAdmin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return null;
    }

    @Override
    public Result findAdminById(int adminId) {
        return Result.SUCCESS(adminRepository.getAdminById(adminId));
    }

    @Override
    public Result findExistedAdminById(int adminId) {
        Admin admin=adminRepository.getAdminById(adminId);
        if (admin==null || admin.getDeleted()==1){
            return Result.FAIL("用户不存在，id为"+adminId);
        }
        return Result.SUCCESS(admin);
    }

    @Override
    public Result update(Admin admin) {
        //为了和传入的参数区分
        Admin adminResult=adminRepository.getAdminById(admin.getAdminId());
        if (adminResult==null){
            return Result.FAIL("不存在这个adminId的管理员，id为"+admin.getAdminId());
        }

        String newEmail=admin.getEmail();//获取新邮箱
        Admin admin1=adminRepository.getAdminByEmail(newEmail);
        //如果返回空或者返回的是自己，说明新邮箱合法
        if (admin1==null || admin1.getAdminId()==admin.getAdminId()){
            adminRepository.update(admin);
            return Result.SUCCESS(admin);
        }
        else {
            return Result.FAIL("邮箱已存在，修改管理员信息失败");
        }
    }

    @Override
    public Result login(LoginRequest loginRequest) {
        Admin admin=adminRepository.login(loginRequest);
        if (admin==null){
            return Result.FAIL("邮箱地址或密码错误");
        }
        return Result.SUCCESS(admin);
    }

    @Override
    public Result uploadAdminAvatar(int adminId, MultipartFile file) {
        String fileName="admin"+adminId+".jpg";
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D:\\WebServer\\resources\\adminAvatars\\" + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片上传成功后，还要更新数据库，将这个admin的图片地址修改为新的地址
        Admin admin=adminRepository.getAdminById(adminId);
        if (admin.getDeleted()==1){
            return Result.FAIL("管理员已经被删除，ID为:"+adminId);
        }
        admin.setAvatar("http://localhost:8099/adminAvatars/"+fileName);
        adminRepository.update(admin);
        return Result.SUCCESS(admin);
    }

    @Override
    public Result getAdminNumber() {
        return Result.SUCCESS(adminRepository.getAdminNumber());
    }

    @Override
    public Result getUserNumber() {
        return Result.SUCCESS(adminRepository.getUserNumber());
    }

    @Override
    public Result getMovieNumber() {
        return Result.SUCCESS(adminRepository.getMovieNumber());
    }

    @Override
    public Result getRatingNumber() {
        return Result.SUCCESS(adminRepository.getRatingNumber());
    }

    @Override
    public Result getMovieMostViewedData() {
        return Result.SUCCESS(adminRepository.getMovieViewedData());
    }

    @Override
    public Result getMovieTopRatedData() {
        return Result.SUCCESS(adminRepository.getMovieTopRatedData());
    }

    @Override
    public Result getAdminList() {
        List<Admin> adminList=adminRepository.getAdminList();
        if (adminList==null){
            return Result.FAIL("管理员列表获取失败");
        }
        return Result.SUCCESS(adminList);
    }

    @Override
    public Result LogicDeleteById(int adminId) {
        Admin admin=adminRepository.getAdminById(adminId);
        if (admin==null){
            return Result.FAIL("不存在这个adminId的管理员，id为"+adminId);
        }
        if (admin.getDeleted()==1){
            return Result.FAIL("这个管理员已经被逻辑删除了，id为"+adminId);
        }
        //将deleted字段修改为1，返回aminId
        adminRepository.logicDeleteById(adminId);
        return Result.SUCCESS(adminId);
    }

    @Override
    public Result recoverAdminById(int adminId) {
        Admin admin=adminRepository.getAdminById(adminId);
        if (admin==null){
            return Result.FAIL("不存在这个管理员，ID为"+adminId);
        }
        if (admin.getDeleted()==0){
            return Result.FAIL("这个管理员没有被逻辑删除，id为"+adminId);
        }
        //将deleted修改为0
        adminRepository.recoverAdminById(adminId);
        return Result.SUCCESS(adminId);
    }
}
