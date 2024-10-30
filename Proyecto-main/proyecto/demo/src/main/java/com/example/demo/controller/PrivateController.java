package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private")
@RequiredArgsConstructor
public class PrivateController {
    
    @PostMapping(value = "demo")
    public String welcome(){
        return "welcome";
    }
}
