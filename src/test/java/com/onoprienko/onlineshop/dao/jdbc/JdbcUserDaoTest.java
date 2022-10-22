package com.onoprienko.onlineshop.dao.jdbc;

import com.onoprienko.onlineshop.entity.User;
import com.onoprienko.onlineshop.utils.database.DataSourceFactory;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcUserDaoTest {
    private final String CREATE_SCRIPT = "create table if not exists users( id      bigserial            not null constraint users_pk primary key, name character varying(250), email character varying(250), password character varying(250),role character varying(250),sole character varying(250))";
    private final String DELETE_SCRIPT = "DELETE FROM users";
    private final String DROP_SCRIPT = "DROP TABLE users";
    private final String URL = "jdbc:postgresql://localhost:5433/testdb";
    private final String PASS = "pass";
    private final String USER = "user";


    @BeforeAll
    public void init() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SCRIPT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
        } catch (SQLException ignore) {

        }
    }

    @Test
    void addUserAndFinUserByEmailWorkCorrect() throws SQLException {
        User userOne = User.builder()
                .role("USER")
                .email("em")
                .name("name")
                .password("pass")
                .sole("dsadsad")
                .build();
        User userTwo = User.builder()
                .role("ADMIN")
                .email("emaiui")
                .name("n")
                .password("password")
                .sole("dsdfldslf")
                .build();
        DataSourceFactory dataSourceFactory = new DataSourceFactory(URL, PASS, USER);
        JdbcUserDao jdbcProductDao = new JdbcUserDao(dataSourceFactory.create());

        jdbcProductDao.add(userOne);
        jdbcProductDao.add(userTwo);

        User userByEmailOne = jdbcProductDao.findByEmail(userOne.getEmail());

        assertNotNull(userByEmailOne);
        assertEquals(userByEmailOne.getName(), userOne.getName());
        assertEquals(userByEmailOne.getEmail(), userOne.getEmail());
        assertEquals(userByEmailOne.getRole(), userOne.getRole());
        assertEquals(userByEmailOne.getPassword(), userOne.getPassword());
        assertEquals(userByEmailOne.getSole(), userOne.getSole());

        User userByEmailTwo = jdbcProductDao.findByEmail(userTwo.getEmail());

        assertNotNull(userByEmailTwo);
        assertEquals(userByEmailTwo.getName(), userTwo.getName());
        assertEquals(userByEmailTwo.getEmail(), userTwo.getEmail());
        assertEquals(userByEmailTwo.getRole(), userTwo.getRole());
        assertEquals(userByEmailTwo.getPassword(), userTwo.getPassword());
        assertEquals(userByEmailTwo.getSole(), userTwo.getSole());
    }

    @AfterEach
    public void afterEach() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCRIPT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @AfterAll
    public void afterAll() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_SCRIPT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
        } catch (SQLException ignore) {

        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}