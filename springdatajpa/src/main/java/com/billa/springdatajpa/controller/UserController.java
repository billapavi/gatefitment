package com.billa.springdatajpa.controller;

import com.billa.springdatajpa.domaine.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping("/show")
    public String showUser() {

        return "new User()";
    }
}
