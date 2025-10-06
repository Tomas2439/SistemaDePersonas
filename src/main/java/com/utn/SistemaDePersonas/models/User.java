package com.utn.SistemaDePersonas.models;

import jakarta.persistence.*;

/**
 * Entidad User que representa un usuario en la base de datos.
 * Contiene username, password, nivel de prioridad y relación con PersonaModel.
 */
@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private Integer priority; // nivel de permisos

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id", nullable = false)
    private PersonaModel persona;

    // Constructores
    public User() {
    }

    public User(Long id, String username, String password, Integer priority, PersonaModel persona) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.priority = priority;
        this.persona = persona;
    }

    // Getters y setters
    public PersonaModel getPersona() {
        return persona;
    }

    public void setPersona(PersonaModel persona) {
        this.persona = persona;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}