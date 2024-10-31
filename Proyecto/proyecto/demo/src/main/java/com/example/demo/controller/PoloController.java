package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;   

import com.example.demo.model.Polo;
import com.example.demo.service.PoloService;

@RestController
@RequestMapping("/polo")
public class PoloController {
    
    @Autowired
    PoloService poloService;


    @GetMapping("")
    public List<Polo> list() {
        return poloService.buscarPolo();
    }

    @PutMapping("/{id}/asignarRol")
    public ResponseEntity<?> asignarRol(@PathVariable int id, @RequestParam String rol){
        poloService.asignarRol(id, rol);
        return ResponseEntity.ok("Rol asignado con éxito");
    }

}
