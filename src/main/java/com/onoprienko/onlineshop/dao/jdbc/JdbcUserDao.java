package com.onoprienko.onlineshop.dao.jdbc;


import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.dao.jdbc.mapper.UsersRowMapper;
import com.onoprienko.onlineshop.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Slf4j
public class JdbcUserDao implements UserDao {
    private static final String ADD_USER = "INSERT INTO users (name, email, password, sole, role) VALUES(?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_EMAIL = "SELECT name, email, password, sole, role FROM users WHERE email = ?";
    private final String CREATE_SCRIPT = "create table if not exists users( id      bigserial            not null constraint users_pk primary key, name character varying(250), email character varying(250), password character varying(250),role character varying(250),sole character varying(250))";
    private final DataSource dataSource;

    private final UsersRowMapper usersRowMapper = new UsersRowMapper();

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SCRIPT);
             ResultSet resultSet = preparedStatement.executeQuery()) {

        }catch (Exception e) {
            log.error("Can not initialize Table products", e);
        }
    }


    @Override
    public void addUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getSole());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Exception while adding user ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return usersRowMapper.mapRow(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            log.error("Exception while finding user by email ", e);
            throw new RuntimeException(e);
        }
    }
}
