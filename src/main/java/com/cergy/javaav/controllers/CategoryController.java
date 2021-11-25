package com.cergy.javaav.controllers;

import com.cergy.javaav.Services.CategoryDao;
import com.cergy.javaav.models.Category;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Category>> getitem(@RequestParam("range") String itemid){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",itemid);
        responseHeaders.set("Accept-Range","category");
        return  ResponseEntity.ok()
                .headers(responseHeaders)
                .body(categoryDao.count(itemid));
        //return categoryDao.count(itemid);
    }

   /* @GetMapping("/add")
    public String getUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }*/

    @PostMapping("")
    public String post(@RequestBody Category category) throws IOException {
        categoryDao.addCategory(category);
        return "Created";
    }

    @PutMapping ("/{id}")
    @ResponseBody
    public String put(@RequestBody Category category, @PathVariable(value="id") int id) throws IOException {
       return categoryDao.putCategory(category, id);

    }

    @DeleteMapping  ("/{id}")
    public String delete(@RequestBody Category category, @PathVariable(value="id") int id) throws IOException {
        return categoryDao.deleteCategory(category,id);

    }

    @GetMapping ("/{id}")
    public List<Category> get(@RequestBody Category category, @PathVariable(value="id") int id) throws IOException {
        return categoryDao.getOneCategory(category,id);

    }


    /*@GetMapping
    public @ResponseBody
    Page<Category> getAllCategories(Pageable pageable){
        return CategoryRepository.findAll(pageable);
    }*/
}
