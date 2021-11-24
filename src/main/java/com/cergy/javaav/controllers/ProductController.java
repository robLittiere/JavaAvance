package com.cergy.javaav.controllers;

import com.cergy.javaav.Services.ProductDao;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


}
