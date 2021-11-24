package com.cergy.javaav.controllers;

import com.cergy.javaav.Services.UserDao;
import com.cergy.javaav.models.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        List<User> list = userDao.listAll3();
        model.addAttribute("users", list);
        return "user/index";
    }

    @GetMapping("/add")
    public String getUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }


    @PostMapping("/add")
    public String submit(@ModelAttribute User user, Model model) {
        userDao.addUser(user);
        return "redirect:/users";
    }


}
