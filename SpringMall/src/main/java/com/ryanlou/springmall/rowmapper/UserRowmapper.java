package com.ryanlou.springmall.rowmapper;

import com.ryanlou.springmall.constant.Role;
import com.ryanlou.springmall.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowmapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreateDate(rs.getTimestamp("created_date"));
        user.setLastModifiedData(rs.getTimestamp("last_modified_date"));

        //        string 轉 enum
        String roleStr = rs.getString("role");
        Role role = Role.valueOf(roleStr);
        user.setRole(role);

        return user;
    }
}
