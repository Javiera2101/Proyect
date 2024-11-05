package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/registroPolo")
    public ResponseEntity<Polo> registrarPolo(@RequestBody Polo polo) {
        Polo nuevoPolo = poloService.registrarPolo(polo);
        return new ResponseEntity<>(nuevoPolo, HttpStatus.CREATED);
    }
    @PutMapping("/{id}/asignarRol")
    public ResponseEntity<?> asignarRol(@PathVariable int id, @RequestParam String rol) {
    poloService.asignarRol(id, rol);
    return ResponseEntity.ok("Rol asignado con éxito");
}
}
