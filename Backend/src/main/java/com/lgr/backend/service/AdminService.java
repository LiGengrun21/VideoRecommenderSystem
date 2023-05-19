package com.lgr.backend.service;

import com.lgr.backend.model.collection.Admin;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Li Gengrun
 * @date 2023/4/13 17:52
 */
@Service
public interface AdminService {
    /**
     * 添加一个管理员
     * @param admin
     * @return
     */
    Result add(Admin admin);
    List<Admin> getAllAdmins();

    Result findAdminById(int adminId);

    Result LogicDeleteById(int adminId);

    Result findExistedAdminById(int adminId);

    Result update(Admin admin);

    Result login(LoginRequest loginRequest);

    Result uploadAdminAvatar(int adminId, MultipartFile file);

    Result getAdminNumber();

    Result getUserNumber();

    Result getMovieNumber();

    Result getRatingNumber();

    Result getMovieMostViewedData();

    Result getMovieTopRatedData();
}
