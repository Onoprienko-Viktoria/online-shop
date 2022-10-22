package com.onoprienko.onlineshop.dao.jdbc;

import com.onoprienko.onlineshop.entity.Product;
import com.onoprienko.onlineshop.utils.DataSourceFactory;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcProductDaoTest {
    private final String CREATE_SCRIPT = "create table if not exists products (id bigserial not null constraint products_pk primary key, name character varying(250), price bigint, creation_date date)";
    private final String DELETE_SCRIPT = "DELETE FROM products";
    private final String DROP_SCRIPT = "DROP TABLE products";
    private final String URL = "jdbc:postgresql://ec2-52-202-236-238.compute-1.amazonaws.com:5432/d67r2cidjccd4v";
    private final String PASS = "79cc06ff8c06f948cd0eee13a9f659e86fb856800025ad68fee662229a71485c";
    private final String USER = "ggrmzqjxubkdrd";

    Product testProductOne = Product.builder()
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test 1")
            .price(1).build();
    Product testProductTwo = Product.builder()
            .creationDate(Date.valueOf(LocalDate.now()))
            .name("test 2")
            .price(2).build();
    Product testProductThree = Product.builder()
            .creationDate(null)
            .name("test 3")
            .price(3).build();

    @BeforeAll
    public void init() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SCRIPT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
        } catch (SQLException ignore) {
        }
    }

    @Test
    void addProductAndFindAllWorkCorrect() throws SQLException {
        DataSourceFactory dataSourceFactory = new DataSourceFactory(URL, PASS, USER);
        DataSource dataSource = dataSourceFactory.create();
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSource);

        jdbcProductDao.add(testProductOne);
        jdbcProductDao.add(testProductTwo);
        jdbcProductDao.add(testProductThree);

        List<Product> all = jdbcProductDao.findAll();
        assertEquals(all.size(), 3);
        assertEquals(all.get(0).getName(), testProductOne.getName());
        assertEquals(all.get(0).getCreationDate(), testProductOne.getCreationDate());
        assertEquals(all.get(0).getPrice(), testProductOne.getPrice());

        assertEquals(all.get(1).getName(), testProductTwo.getName());
        assertEquals(all.get(1).getCreationDate(), testProductTwo.getCreationDate());
        assertEquals(all.get(1).getPrice(), testProductTwo.getPrice());

        assertEquals(all.get(2).getName(), testProductThree.getName());
        assertEquals(all.get(2).getCreationDate(), testProductThree.getCreationDate());
        assertEquals(all.get(2).getPrice(), testProductThree.getPrice());
    }


    @Test
    void removeProductWorkCorrect() throws SQLException {
        DataSourceFactory dataSourceFactory = new DataSourceFactory(URL, PASS, USER);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSourceFactory.create());

        jdbcProductDao.add(testProductOne);
        jdbcProductDao.add(testProductTwo);

        List<Product> before = jdbcProductDao.findAll();
        assertEquals(before.size(), 2);

        jdbcProductDao.remove(1L);

        List<Product> after = jdbcProductDao.findAll();
        assertEquals(after.size(), 1);
    }

    @Test
    void editProduct() throws SQLException {
        DataSourceFactory dataSourceFactory = new DataSourceFactory(URL, PASS, USER);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSourceFactory.create());

        jdbcProductDao.add(testProductOne);
        jdbcProductDao.add(testProductTwo);

        List<Product> before = jdbcProductDao.findAll();
        assertEquals(before.size(), 2);
        assertEquals(before.get(0).getName(), "test 1");
        assertEquals(before.get(1).getName(), "test 2");

        testProductThree.setId(before.get(0).getId());
        jdbcProductDao.edit(testProductThree);

        List<Product> after = jdbcProductDao.findAll();
        assertEquals(after.size(), 2);
        assertEquals(after.get(0).getName(), "test 2");
        assertEquals(after.get(1).getName(), "test 3");
    }

    @Test
    void findProductsByWordsIn() throws SQLException {
        DataSourceFactory dataSourceFactory = new DataSourceFactory(URL, PASS, USER);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSourceFactory.create());

        jdbcProductDao.add(testProductOne);
        jdbcProductDao.add(testProductTwo);
        jdbcProductDao.add(testProductThree);

        List<Product> productsByWordIn = jdbcProductDao.findAllByWordIn("3");
        assertEquals(productsByWordIn.size(), 1);
        assertTrue(productsByWordIn.get(0).getName().contains("3"));
        assertEquals(productsByWordIn.get(0).getName(), testProductThree.getName());
        assertEquals(productsByWordIn.get(0).getPrice(), testProductThree.getPrice());
        assertEquals(productsByWordIn.get(0).getCreationDate(), testProductThree.getCreationDate());

    }

    @Test
    void findProductById() throws SQLException {
        DataSourceFactory dataSourceFactory = new DataSourceFactory(URL, PASS, USER);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSourceFactory.create());

        jdbcProductDao.add(testProductThree);


        List<Product> products = jdbcProductDao.findAll();
        assertEquals(products.size(), 1);
        assertEquals(products.get(0).getName(), testProductThree.getName());
        assertEquals(products.get(0).getPrice(), testProductThree.getPrice());
        assertEquals(products.get(0).getCreationDate(), testProductThree.getCreationDate());

        Product product = jdbcProductDao.getById(products.get(0).getId());

        assertEquals(product.getName(), testProductThree.getName());
        assertEquals(product.getPrice(), testProductThree.getPrice());
        assertEquals(product.getCreationDate(), testProductThree.getCreationDate());
        assertEquals(product.getId(), products.get(0).getId());

    }

    @AfterEach
    public void afterEach() throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCRIPT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
        } catch (SQLException ignore) {
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