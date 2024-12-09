package com.example.demo.security;

import org.springframework.stereotype.Component;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Component
public class PasswordHasher {
    private final Argon2 argon2;

    public PasswordHasher() {
        this.argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id, 
            16,   // Longitud del salt 
            32    // Longitud del hash
        );
    }

    // Método para hashear contraseñas nuevas o migrar
    public String hashPassword(String password) {
        return argon2.hash(
            2,          // número de iteraciones
            64 * 1024,  // memoria en KB (64MB)
            1,          // paralelismo
            password.toCharArray()
        );
    }

    // Método para verificar contraseñas
    public boolean verifyPassword(String hash, String password) {
        return argon2.verify(hash, password.toCharArray());
    }
}
