package com.lgr.backend.model.request;

/**
 * @author Li Gengrun
 * @date 2023/5/21 10:24
 */
public class AdminRegisterRequest {

    private String adminName;

    private String password;

    private String email;

    public AdminRegisterRequest() {
    }

    public AdminRegisterRequest(String adminName, String password, String email) {
        this.adminName = adminName;
        this.password = password;
        this.email = email;
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
}
