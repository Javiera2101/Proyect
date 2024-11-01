package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping({"/", "/home", "/index"})
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Esto se refiere a login.html en la carpeta templates
    }
}
