package com.onoprienko.onlineshop.dao.jdbc.mapper;

import com.onoprienko.onlineshop.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsersRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        UsersRowMapper usersRowMapper = new UsersRowMapper();
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getString("name"))
                .thenReturn("Tom");
        when(resultSetMock.getString("email"))
                .thenReturn("tom@gmail.com");
        when(resultSetMock.getString("sole"))
                .thenReturn("sole");
        when(resultSetMock.getString("role"))
                .thenReturn("USER");
        when(resultSetMock.getString("password"))
                .thenReturn("pass");

        User user = usersRowMapper.mapRow(resultSetMock);

        assertNotNull(user);
        assertEquals(user.getSole(), "sole");
        assertEquals(user.getEmail(), "tom@gmail.com");
        assertEquals(user.getRole(), "USER");
        assertEquals(user.getName(), "Tom");
        assertEquals(user.getPassword(), "pass");
    }
}