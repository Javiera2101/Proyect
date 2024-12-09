package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.service.PasswordMigrationService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private PasswordMigrationService migrationService;

    @Override
    public void run(String... args) {
        migrationService.migrarContrasenas();
    }
}