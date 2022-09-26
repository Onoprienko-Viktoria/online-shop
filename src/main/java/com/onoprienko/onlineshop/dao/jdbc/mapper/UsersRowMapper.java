package com.onoprienko.onlineshop.dao.jdbc.mapper;

import com.onoprienko.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        return User.builder()
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .sole(resultSet.getString("sole"))
                .role(resultSet.getString("role"))
                .password(resultSet.getString("password"))
                .build();

    }
}
