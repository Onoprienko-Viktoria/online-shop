package com.onoprienko.onlineshop.dao.jdbc.mapper;

import com.onoprienko.onlineshop.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductsRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDate = LocalDateTime.parse("2022-04-08 12:30", formatter);
        ProductsRowMapper productsRowMapper = new ProductsRowMapper();
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getString("name")).thenReturn("Cat");
        when(resultSetMock.getInt("id")).thenReturn(111);
        when(resultSetMock.getDouble("price")).thenReturn(300.0);
        when(resultSetMock.getTimestamp("creation_date")).thenReturn(Timestamp.valueOf(localDate));

        Product actual = productsRowMapper.mapRow(resultSetMock);
        assertEquals(111, actual.getId());
        assertEquals("Cat", actual.getName());
        assertEquals(300.0, actual.getPrice());
        assertEquals(localDate.toString(), actual.getCreationDate().toString());
    }
}