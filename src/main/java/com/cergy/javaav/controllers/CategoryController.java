package com.cergy.javaav.controllers;

import com.cergy.javaav.Services.CategoryDao;
import com.cergy.javaav.models.Category;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Category> index(Model model){
        List<Category> list = categoryDao.listAll3();
        return list;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public List<Category> getById(@PathVariable(value = "id") int id, Category category){
        return (List<Category>) categoryDao.getCategoryById(id,category);
    }

   /* @GetMapping("/add")
    public String getUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }*/

    @PostMapping("")
    public String submit(@RequestBody Category category) throws IOException {
        categoryDao.addCategory(category);
        return "Created";
    }
}
