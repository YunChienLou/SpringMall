package com.ryanlou.springmall.service;

import com.ryanlou.springmall.dto.UserRegisterRequest;
import com.ryanlou.springmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
