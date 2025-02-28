package com.zekaya.eccommerce.service.interf;

import com.zekaya.eccommerce.dto.LoginRequest;
import com.zekaya.eccommerce.dto.Response;
import com.zekaya.eccommerce.dto.UserDto;
import com.zekaya.eccommerce.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getLoginUser();

    Response getUserInfoAndOrderHistory();
}
