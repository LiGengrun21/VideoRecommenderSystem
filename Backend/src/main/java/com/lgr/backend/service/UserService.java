package com.lgr.backend.service;

import com.lgr.backend.model.collection.User;
import com.lgr.backend.model.request.LoginRequest;
import com.lgr.backend.model.request.RegisterRequest;
import com.lgr.backend.util.Result;
import org.springframework.stereotype.Service;

/**
 * @author Li Gengrun
 * @date 2023/4/13 17:51
 */
@Service
public interface UserService {
    Result login(LoginRequest loginRequest);

    Result register(RegisterRequest registerRequest);
}
