package com.ryanlou.springmall.dao.impl;

import com.ryanlou.springmall.dao.UserDao;
import com.ryanlou.springmall.dto.UserRegisterRequest;
import com.ryanlou.springmall.model.User;
import com.ryanlou.springmall.rowmapper.UserRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user (email , password , created_date, last_modified_date, role) " +
                "VALUES (:email , :password , :createdDate , :lastModifiedDate , :role)";
        Map<String , Object> map = new HashMap<>();

        map.put("email" , userRegisterRequest.getEmail());
        map.put("password" , userRegisterRequest.getPassword());
        map.put("role" , "USER");

        Date now = new Date();

        map.put("createdDate" , now);
        map.put("lastModifiedDate" , now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql , new MapSqlParameterSource(map) , keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId ;
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id, email , password , created_date, last_modified_date , role " +
                "FROM user WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();

        map.put("userId" , userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map, new UserRowmapper());

        if(!userList.isEmpty()){
            return userList.get(0);
        }else {
            return null;
        }

    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email , password , created_date, last_modified_date , role " +
                "FROM user WHERE email = :email";

        Map<String, Object> map = new HashMap<>();

        map.put("email" , email);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map, new UserRowmapper());

        if(!userList.isEmpty()){
            return userList.get(0);
        }else {
            return null;
        }
    }
}
