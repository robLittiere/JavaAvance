package com.cergy.javaav.Services;

import com.cergy.javaav.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Service
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate template;

    public List<User> listAll3(){
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM User";
        list = template.query(query, BeanPropertyRowMapper.newInstance(User.class));
        return list;
    }

    public void addUser(User user){
        String query = "INSERT INTO User (firstname, lastname, email) values (?, ?, ?)";
        template.update(query, user.getFirstname(), user.getLastname(), user.getEmail());
    }
}
