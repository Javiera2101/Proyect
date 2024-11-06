package com.example.demo.service;

public interface LoginService {
    String autenticarUsuario(String correo, String contrasena) throws Exception;
    String obtenerNombreUsuario(String correo);
}