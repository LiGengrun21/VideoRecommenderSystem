package com.lgr.backend.model.Response;

/**
 * @author Li Gengrun
 * @date 2023/5/1 15:07
 */
public class ShowUserProfileResponse {

    private String username;

    private String email;

    private String password;

    private String avatar;

    public ShowUserProfileResponse() {
    }

    public ShowUserProfileResponse(String username, String email, String password, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
