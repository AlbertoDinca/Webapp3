package com.link.Webapp3.controller;

import com.link.Webapp3.model.RegisterForm;
import com.link.Webapp3.model.User;
import com.link.Webapp3.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/users")
    public ModelAndView getUsers(){
        ModelAndView userView = new ModelAndView("users.html");
       String query = "SELECT * FROM `auto`.`user`";
        List<User> userList = jdbcTemplate.query(query, new UserRowMapper());
       userView.addObject("users", userList);
       return userView;

    }

    @GetMapping("/users/{id}")
    public ModelAndView getUserById(@PathVariable Integer id){
        ModelAndView userView = new ModelAndView("userProfile.html");
        String query = "SELECT * FROM `auto`.`user` WHERE id="+ id;
        User currentUser = jdbcTemplate.queryForObject(query, new UserRowMapper());
        userView.addObject("user", currentUser);
        return userView;

    }

    @PostMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView updateUserById(@PathVariable Integer id, RegisterForm data){
        ModelAndView userView = new ModelAndView("userProfile.html");
        String sqlQuery="UPDATE `auto`.`user`(`first_name`,`last_name`,`email`,`phone`) VALUES(?,?,?,?) WHERE `id` =" + id;
        int result = jdbcTemplate.update(sqlQuery,data.getFirstName(),data.getLastname(), data.getEmail(), data.getPhone());
        return userView;
    }
    //un endpoint care sa afiseze toti utilizatorii

    //un endpoint care sa stearga un utilizator
}
