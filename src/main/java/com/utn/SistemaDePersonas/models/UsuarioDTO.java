package com.utn.SistemaDePersonas.models;

import jakarta.validation.constraints.*;

/**
 * DTO para la transferencia de datos de la entidad User.
 * Se usa principalmente para la creación de usuarios.
 */
public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    private Integer priority;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

    @NotNull(message = "La persona es obligatoria")
    private Long personaId;

    // Constructores
    public UsuarioDTO() {
    }

    public UsuarioDTO(String username, String password, Long personaId) {
        this.username = username;
        this.password = password;
        this.personaId = personaId;
    }

    // Getters y setters
    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
