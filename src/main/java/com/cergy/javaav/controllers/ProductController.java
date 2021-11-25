package com.cergy.javaav.controllers;

import com.cergy.javaav.Services.ProductDao;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Product> index(@RequestParam (required = false) String asc, @RequestParam (required = false) String desc, @RequestParam (required = false) Map<String,String> params){
        // Regarde si l'utilisateur a saisit desc dans la requète
        if (desc != null){
            if(desc.contains(",")){
                return productDao.listAllBySameOrder(asc, desc);
            }
        }
            // Regarde si l'utilisateur a saisit asc dans la requète
        if (asc != null){
            if(asc.contains(",")){
                return productDao.listAllBySameOrder(asc, desc);
            }
        }
        // Récupère un string contenant DESC ou ASC pour connaître l'ordre de rentrée des paramètres
        if(params.containsKey("asc") || params.containsKey("desc")){
            String firstParam = params.keySet().stream().findFirst().get();
            return productDao.listAll(asc , desc, firstParam);
        }
        //Si on a des paramètres, mais ceux ne sont pas ASC et DESC
        if (!params.isEmpty() && !params.containsKey("asc") && !params.containsKey("desc")){
            return productDao.listFiltered(params);

        }

        return productDao.listAll(asc , desc, null);

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addProduct(@RequestBody Product product){
        return productDao.addProduct(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateProduct(@PathVariable(value="id") int id, @RequestBody Product product){
        return productDao.updateProduct(product, id);
        //return productDao.updateProduct(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getById(@PathVariable(value="id") int id){
        Product product =  productDao.getProductById(id);
        return product;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteById(@PathVariable(value="id") int id){
        String validation = productDao.deleteProductById(id);
        return validation;
    }


}
