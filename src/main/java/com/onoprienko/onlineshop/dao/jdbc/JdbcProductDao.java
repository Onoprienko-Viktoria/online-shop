package com.onoprienko.onlineshop.dao.jdbc;

import com.onoprienko.onlineshop.dao.ProductDao;
import com.onoprienko.onlineshop.dao.jdbc.mapper.ProductsRowMapper;
import com.onoprienko.onlineshop.entity.Product;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class JdbcProductDao implements ProductDao {
    private static final String FIND_ALL_PRODUCTS_SQL = "SELECT id, name, price, creation_date FROM Products";
    private static final String UPDATE_PRODUCTS_SQL = "UPDATE products SET name = ?, price = ? WHERE id = ? ";
    private static final String ADD_PRODUCT_SQL = "INSERT INTO products (name, price, creation_date) VALUES(?, ?, ?)";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE id = ?";
    private static final String FIND_ALL_CONTAINS_WORD = "SELECT id, name, price, creation_date FROM Products WHERE name like concat('%', ?, '%')";
    private static final String FIND_PRODUCT_BY_ID = "SELECT id, name, price, creation_date FROM Products WHERE id = ?";
    private final DataSource dataSource;

    private final ProductsRowMapper rowMapper = new ProductsRowMapper();

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
        try (Connection connection = dataSource.getConnection()) {
        } catch (Exception e) {
            log.error("Can not initialize Table products: ", e);
        }
    }

    @Override
    @SneakyThrows
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(rowMapper.mapRow((resultSet)));
            }
            return products;
        }
    }


    @Override
    @SneakyThrows
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public void remove(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public void edit(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTS_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public List<Product> findAllByWordIn(String word) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CONTAINS_WORD)) {
            preparedStatement.setString(1, word);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    products.add(rowMapper.mapRow((resultSet)));
                }
                return products;
            }
        }
    }

    @Override
    @SneakyThrows
    public Product getById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return rowMapper.mapRow((resultSet));
            }
        }
    }
}
