package com.cergy.javaav.Services;

import com.cergy.javaav.models.Product;
import com.cergy.javaav.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate template;


    public List<Product> listAll() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM product";
        list = template.query(query, BeanPropertyRowMapper.newInstance(Product.class));
        return list;
    }
}
