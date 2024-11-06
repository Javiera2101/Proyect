package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        // Si ya hay una sesión activa, redirigir al dashboard correspondiente
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (tipoUsuario != null) {
            return "redirect:/dashboard-" + tipoUsuario;
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String correo = loginData.get("correo");
        String contrasena = loginData.get("contrasena");

        try {
            String tipoUsuario = loginService.autenticarUsuario(correo, contrasena);
            String nombreUsuario = loginService.obtenerNombreUsuario(correo);
            
            session.setAttribute("tipoUsuario", tipoUsuario);
            session.setAttribute("nombreUsuario", nombreUsuario);
            session.setAttribute("correoUsuario", correo);
            
            return ResponseEntity.ok().body(Map.of("redirectUrl", "/"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().body(Map.of("redirectUrl", "/"));
    }

    @GetMapping("/verificar-sesion")
    @ResponseBody
    public Map<String, Object> verificarSesion(HttpSession session) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        boolean sesionActiva = tipoUsuario != null;
        return Map.of(
            "sesionActiva", sesionActiva,
            "tipoUsuario", tipoUsuario != null ? tipoUsuario : ""
        );
    }
}