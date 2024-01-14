package com.ryanlou.springmall.service;

import com.ryanlou.springmall.dto.UserLoginRequest;
import com.ryanlou.springmall.dto.UserRegisterRequest;
import com.ryanlou.springmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    User login(UserLoginRequest userLoginRequest);
}
