package com.lgr.backend.service.impl;

import com.lgr.backend.model.collection.Admin;
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
    public Result add(Admin admin) {
        //原集合里没有这个ID，可以插入
       if (adminRepository.getAdminById(admin.getAdminId())==null){
           return Result.SUCCESS(adminRepository.add(admin));
       }
       return Result.FAIL("集合里已经存在"+admin.getAdminId());
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
        adminRepository.update(admin);
        return Result.SUCCESS(admin);
    }

    @Override
    public Result login(LoginRequest loginRequest) {
        Admin admin=adminRepository.login(loginRequest);
        if (admin==null){
            return Result.FAIL("邮箱地址或密码错误");
        }
        return Result.SUCCESS(admin);
    }
}
