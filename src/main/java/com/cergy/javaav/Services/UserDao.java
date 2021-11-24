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

    // Open a connection
    final String DB_URL = "jdbc:mysql://localhost:8889/JavaTest";
    final String USER = "root";
    final String PASS = "root";

    public List<User> listAll() {
        List<User> list = new ArrayList<>();
        final String QUERY = "SELECT * FROM User";


        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)){;
            // Création de la query
            try(Statement stmt = conn.createStatement()){
                // Résultat de la query
                try(ResultSet rs = stmt.executeQuery(QUERY)){
                    // Extract data from result set
                    while (rs.next()) {
                        User u = new User();
                        u.setFirstname(rs.getString("firstname"));
                        u.setLastname(rs.getString("lastname"));
                        u.setEmail(rs.getString("email"));
                        list.add(u);
                    }
                };

            };


        } catch (SQLException e) {
            e.printStackTrace();
        }


        User v = new User();
        v.setFirstname("Paul");
        v.setLastname("Moooche");
        v.setEmail("paul@mail.com");
        v.setId(2);
        list.add(v);

        return list;
    }

    public List<User> listAll2(){
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM User";
        list = template.query(query, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User u = new User();
                u.setFirstname(rs.getString("firstname"));
                u.setLastname(rs.getString("lastname"));
                u.setEmail(rs.getString("email"));
                return u;
            }
        });


        return list;
    }

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
