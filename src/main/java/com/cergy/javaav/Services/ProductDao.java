package com.cergy.javaav.Services;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
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

    public Product getProductById(int id) {
        String query = "SELECT * FROM product WHERE id = ?";
        Product product = template.queryForObject(query, BeanPropertyRowMapper.newInstance(Product.class), id);
        return product;
    }

    public String deleteProductById(int id) {

        String query = "DELETE FROM product WHERE id = ?";
        int result = template.update(query, id);
        if(result == 1){
            return "Product deleted";
        }
        return "Product not found";
    }

    public String addProduct(Product product) {
        // Ajouter la date au produit
        Date date = new Date();
        product.setCreatedAt(date);

        // Création de la query MySQL
        String query = "INSERT INTO product (type, rating, name, createdAt, categoryId) values (?, ?, ?, ?, ?)";
        int result = template.update(query, product.getType(), product.getRating(), product.getName(), product.getCreatedAt(), product.getCategoryId());
        // Si la requète est réussit :
        if(result == 1){
            return "Product added";
        }
        return "Error";
    }

    public String updateProduct(Product product, int id) {
        String query = "UPDATE product SET type = ?, rating = ?, name = ?, categoryId = ?  WHERE id = ?";
        int result = template.update(query, product.getType(), product.getRating(), product.getName(), product.getCategoryId(), id);
        // Si la requète est réussit :
        if(result == 1){
            return "Product added";
        }
        return "Error";


    }
}
