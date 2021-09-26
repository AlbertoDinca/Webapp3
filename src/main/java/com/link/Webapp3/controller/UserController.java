package com.link.Webapp3.controller;

import com.link.Webapp3.model.LoginForm;
import com.link.Webapp3.model.RegisterForm;
import com.link.Webapp3.model.User;
import com.link.Webapp3.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //endpoint care intoarce register.html -> GET
    @GetMapping("/register")
    public ModelAndView getRegisterPage(){
        return new ModelAndView("register.html");
    }


    //endpoint care proceseaza cererea de registrare -> POST
@PostMapping(value = "/register-result", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public ModelAndView postRegister(RegisterForm data){
ModelAndView registerView = new ModelAndView("register.html");

//validare parole
    if (!data.getPassword().equals(data.getPassword2())){
        registerView.addObject("errPassword","Passwords do not match");
        return registerView;
    }

    //email unic
    String emailQuery = "SELECT COUNT(*) FROM `user` WHERE email = '" + data.getEmail() + "'";
    Integer emailCount =jdbcTemplate.queryForObject(emailQuery, Integer.class);

    if (emailCount > 0 ){
        registerView.addObject("errEmail", "Email already in use");
        return  registerView;
    }
    //adaugam user in baza de date
    String sqlQuery="INSERT INTO `user`(`first_name`,`last_name`,`email`,`password`,`phone`) VALUES(?,?,?,?,?);";
    int result = jdbcTemplate.update(sqlQuery,data.getFirstName(),data.getLastname(), data.getEmail(), data.getPassword(), data.getPhone());
    



return registerView;
}
    //endpoint care intoare cerere login.html -> GET
    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        return new ModelAndView("login.html");
    }
    //endpoint care intoare login.html -> POST

    @PostMapping(value = "/login-result", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postLogin(LoginForm data) {
        ModelAndView loginView = new ModelAndView("login.html");

        //verifica, ca exista user cu email si pass
      String userQuery = "SELECT * FROM `user` WHERE email = '" + data.getEmail()+"' AND password ='"+ data.getPassword()+"'";
       List<User> userList = jdbcTemplate.query(userQuery,new UserRowMapper());
        //daca nu, afisam eroare
        if (userList.size() == 0){
            loginView.addObject("errLogin", "Email or password are incorrect");
            return loginView;
        } else {
            return new ModelAndView("redirect:/dashboard");
        }

    }}
