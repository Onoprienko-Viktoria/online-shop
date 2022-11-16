package com.onoprienko.onlineshop.dao.jdbc;


import com.onoprienko.ioc.annotation.PostConstruct;
import com.onoprienko.onlineshop.dao.UserDao;
import com.onoprienko.onlineshop.dao.jdbc.mapper.UsersRowMapper;
import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.utils.database.DataSourceFactory;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;


@Slf4j
@NoArgsConstructor
@Setter
public class JdbcUserDao implements UserDao {
    private static final String ADD_USER = "INSERT INTO users (name, email, password, sole, role) VALUES(?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_EMAIL = "SELECT name, email, password, sole, role FROM users WHERE email = ?";
    private DataSource dataSource;
    private DataSourceFactory dataSourceFactory;

    private final static UsersRowMapper USERS_ROW_MAPPER = new UsersRowMapper();

    @SneakyThrows
    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        if (this.dataSource == null) {
            this.dataSource = dataSourceFactory.create();
        }
    }

    @Override
    @SneakyThrows
    public void add(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getSole());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return Optional.of(USERS_ROW_MAPPER.mapRow(resultSet));
                }
                return Optional.empty();
            }
        }
    }
}
