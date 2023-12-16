package com.ryanlou.springmall.dao;

import com.ryanlou.springmall.dto.UserRegisterRequest;
import com.ryanlou.springmall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
