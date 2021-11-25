package com.cergy.javaav.Services;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate template;

    /**
     *Récupère la query, la traite, puis renvoie une liste de produit triée.
     * @param ascColumnName nom de la colonne ASC récupérée dans la requète
     * @param descColumnName nom de la colonne DESC récupérée dans la requète
     * @return Une liste de Produit triée ou non
     */
    public List<Product> listAll(String ascColumnName,String descColumnName, String firstParamEntered) {
        List<Product> list = new ArrayList<>();
        // Init une query basique
        String query = "SELECT * FROM product";
        // Récupère toutes les colonnes du produit présentes dans la bdd
        String[] columns = {"name", "type", "categoryId", "rating", "createdAt", "updatedAt"};

        // On regarde si on a les deux tris de présents
        if (ascColumnName != null && descColumnName != null){
            // Init deux colonnes qu'on va récupérer
            String ascCol = "";
            String descCol = "";
            // Boucle sur la liste des colonnes
            for (String column : columns) {
                // Si on trouve les colonne, on les garde dans une variable
                if (column.equals(descColumnName)) {
                    descCol = column;
                }
                if (column.equals(ascColumnName)) {
                    ascCol = column;
                }
            }
            // On vérifie bien qu'on les colonnes entrées existent bien dans la bdd
            if (!ascCol.equals("") && !descCol.equals("")){

                // On regarde si le premier paramètre est asc ou desc
                    if(firstParamEntered.equals("asc")){
                        query = "SELECT * FROM product ORDER by " + ascCol + " ASC, " + descCol + " DESC";
                    }
                    else {
                        query = "SELECT * FROM product ORDER by " + descCol + " DESC, " + ascCol + " ASC";

                    }
            }
        }
        // On regarde si le tri asc est présent
        else if(ascColumnName != null){
            for (String column : columns) {
                if (column.equals(ascColumnName)) {
                    query = "SELECT * FROM product ORDER by " + column + " ASC";
                    break;
                }
            }
        }
        else if(descColumnName != null) {
            for (String column : columns) {
                if (column.equals(descColumnName)) {
                    query = "SELECT * FROM product ORDER by " + column + " DESC";
                    break;
                }
            }
        }
        System.out.println(query);
        list = template.query(query, BeanPropertyRowMapper.newInstance(Product.class));
        return list;
    }

    /**
     * Récupère un ordre type asc/asc ou desc/desc et retourne la liste de produit
     * @return
     */
    public List<Product> listAllBySameOrder(String ascColumn, String descColumn){
        String[] columns = {"name", "type", "categoryId", "rating", "createdAt", "updatedAt"};

        if (ascColumn == null){
            // On récupère les entrées dans une liste
            String[] entries = descColumn.split(",");

            // Init les deux colonnes qu'on va récupérer
            String descCol1 = "";
            String descCol2 = "";
            // Boucle sur la liste des entrées
            for(String entry :  entries){
                // Boucle sur les colonnes de db
                for (String column : columns){
                    if (entry.equals(column)){
                        if(descCol1.equals("")){
                            descCol1 = column;
                        }
                        else {
                            descCol2 = column;
                        }
                    }
                }
            }

            //On écrit la query
            String query = "SELECT * FROM product ORDER by " + descCol1 + " DESC, " + descCol2 + " DESC ";
            // On return le résultat de la query
            System.out.println(query);
            return template.query(query, BeanPropertyRowMapper.newInstance(Product.class));
        }
        else {
            // On récupère les entrées dans une liste
            String[] entries = ascColumn.split(",");
            // Init les deux colonnes qu'on va récupérer
            String ascCol1 = "";
            String ascCol2 = "";
            // Boucle sur la liste des entrées
            for(String entry :  entries){
                // Boucle sur les colonnes de db
                for (String column : columns){
                    if (entry.equals(column)){
                        if(ascCol1.equals("")){
                            ascCol1 = column;
                        }
                        else {
                            ascCol2 = column;
                        }
                    }
                }
            }
            // On écrit la query avec les deux ASC
            String query = "SELECT * FROM product ORDER by " + ascCol1 + " , " + ascCol2;
            // On return le résultat de la query
            System.out.println(query);
            return template.query(query, BeanPropertyRowMapper.newInstance(Product.class));
        }
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
        //Date date = new Date();
        //product.setCreatedAt(date);
        //product.setUpdatedAt(date);

        // Création de la query MySQL
        String query = "INSERT INTO product (type, rating, name, createdAt, categoryId, updatedAt) values (?, ?, ?, NOW(), ?, NOW())";
        int result = template.update(query, product.getType(), product.getRating(), product.getName(), product.getCategoryId());
        // Si la requète est réussit :
        if(result == 1){
            return "Product added";
        }
        return "Error";
    }

    public String updateProduct(Product product, int id) {
        if(product.getId() != id){
            return "Error";
        }
        String query = "UPDATE product SET type = ?, rating = ?, name = ?, categoryId = ?, updatedAt = NOW()  WHERE id = ?";
        int result = template.update(query, product.getType(), product.getRating(), product.getName(), product.getCategoryId(), id);
        // Si la requète est réussit :
        if(result == 1){
            return "Product modified";
        }
        return "Error";
    }

    /**
     * Renvoie une liste de produits filtrée en fonction des paramètres entrés
     * Créer une requète SQL en fonction des paramètres
     * @param params les paramètres rentrées dans l'uri
     * @return Une liste de produits filtrée
     */
    public List<Product> listFiltered(Map<String, String> params) {
        // Init la query initiale
        String query = "SELECT * FROM product WHERE ";
        // Init un offset
        int i = 1;
        // Boucle sur les paramètres
        for (String key : params.keySet()){
            // On check si on est sur la dernière itération pour enlever le AND final de la query
            if (i == params.size()){
                // Si oui, on écrit la fin de la query
                query += key + "=" + "'" + params.get(key) + "'";
                // Si la dernière clé contient plusieurs valeurs
                if(params.get(key).contains(",")){
                    // On récupère les valeurs des paramètres dans un array
                    String[] column = params.get(key).split(",");
                    // On itère sur l'array et on écrit la query
                    for (int j = 0; j < column.length; j++){
                        if(j == column.length){
                            query += key + "=" + "'" + params.get(key);
                        }
                        else {
                            query += key + "=" + "'" + params.get(key) + "' OR ";
                        }
                    }
                }
            }
            // Tant qu'on est pas sur la dernière itération, on écrit normalement notre query
            else{
                // On regarde si la clé contient plusieurs valeurs
                if(params.get(key).contains(",")){
                    // On récupère les valeurs des paramètres dans un array
                    String[] column = params.get(key).split(",");
                    // On itère sur l'array et on écrit la query
                    for (int j = 0; j < column.length; j++){
                        if(j == column.length -1){
                            query += key + "=" + "'" + column[j] +"') AND ";

                        }
                        else {
                            if(j == 0){
                                query += "(" + key + "=" + "'" + column[j] + "' OR ";
                            }
                            else{
                                query += key + "=" + "'" + column[j] + "' OR ";

                            }
                        }
                    }
                }
                else{
                    query += key + "=" + "'" + params.get(key) + "' AND ";
                }
                // On ajoute +1 à l'offset
                i++;
            }

        }
        System.out.println(query);
        return template.query(query, BeanPropertyRowMapper.newInstance(Product.class));
    }
}
