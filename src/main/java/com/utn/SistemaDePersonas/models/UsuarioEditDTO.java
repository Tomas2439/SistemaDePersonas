package com.utn.SistemaDePersonas.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO usado para la edición de usuarios existentes.
 * Contiene username obligatorio y password opcional.
 */
public class UsuarioEditDTO {
    @NotBlank(message = "El username no puede estar vacío")
    private String username;

    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password; // opcional

    // Constructores
    public UsuarioEditDTO() {
    }

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
