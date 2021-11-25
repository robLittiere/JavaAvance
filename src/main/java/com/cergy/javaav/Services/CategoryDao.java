package com.cergy.javaav.Services;

import com.cergy.javaav.models.Category;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoryDao {

    @Autowired
    private JdbcTemplate template;

    public List<Category> getCategoryById(int id, Category category) {
            List<Category> list = new ArrayList<>();
            String query = "SELECT * FROM category WHERE id = ?";
            list = template.query(query, BeanPropertyRowMapper.newInstance(Category.class), id);

            return list;
    }

    public void addCategory(Category category) {
        String query = "INSERT INTO Category (id, name) values (?, ?)";
        template.update(query, category.getId(), category.getName());

    }

    public List<Category> listAll3(){
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM category";
        list = template.query(query, BeanPropertyRowMapper.newInstance(Category.class));
        return list;


    }
}
