package com.example.demo.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcademicoDashboardController {

    @GetMapping("/dashboard/academico")
    public String dashboardAcademico() {
        return "dashboard_academico";
    }
}
