package com.cergy.javaav.Services;

import com.cergy.javaav.models.Category;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


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

    public String putCategory(Category category, int id) {
        String query = "UPDATE category SET name = ?  WHERE id = ?";
        int result = template.update(query, category.getName(), id);
        // Si la requète est réussit :
        if(result == 1){
            return "Category updated";
        }
        return "Error";


    }

    public String deleteCategory(Category category, int id) {
        String query = "DELETE FROM category WHERE id = ?";
        int result = template.update(query, id);
        // Si la requète est réussit :
        if(result == 1){
            return "Category deleted";
        }
        return "Error";
    }

    public List<Category> getOneCategory(Category category, int id) {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM category WHERE id = ?";
       // int result = template.update(query, id);
        list = template.query(query, BeanPropertyRowMapper.newInstance(Category.class), id);

        return list;
    }

    /**
     * Renvoie une liste de produit avec une range donnée
     * @param itemid la range souhaitée obtenu depuis les paramètres de la requète
     * @return la liste des catégories finale
     */
    public List<Category> count(String itemid){
        String[] words = itemid.split("-");
        List<Category> list = new ArrayList<>();
        List<Category> List = new ArrayList<>();
        String query = "SELECT * FROM category";
        list = template.query(query, BeanPropertyRowMapper.newInstance(Category.class));
        for (int i = Integer.parseInt(words[0]); i<Integer.parseInt(words[1]);i++){
            List.add(list.get(i));
        }
        return List;


    }


}
