package com.lgr.backend.model.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
//import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Li Gengrun
 * @date 2023/4/3 16:39
 */
//@Data
//@Builder
//@Document(collection = "Admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Admin {

    @JsonIgnore
    private String _id;

    private int adminId;

    private String adminName;

    private String password;

    private String email;

    private String avatar; //管理员头像

    private int deleted; //逻辑删除字段，0为正常管理员，1为删除管理员

    private int permission; //权限，0为普通管理员，1为超级管理员

    public Admin() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Admin(String _id, int adminId, String adminName, String password, String email, String avatar, int deleted, int permission) {
        this._id = _id;
        this.adminId = adminId;
        this.adminName = adminName;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.deleted = deleted;
        this.permission = permission;
    }
}
