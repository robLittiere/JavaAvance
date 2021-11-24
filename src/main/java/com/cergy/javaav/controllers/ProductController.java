package com.cergy.javaav.controllers;

import com.cergy.javaav.Services.ProductDao;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Product> index(){
        List<Product> list = productDao.listAll();
        return list;
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
