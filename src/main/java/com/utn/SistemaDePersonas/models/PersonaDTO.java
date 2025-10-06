package com.utn.SistemaDePersonas.models;

import jakarta.validation.constraints.NotEmpty;

/**
 * DTO para la transferencia de datos de la entidad Persona.
 * Contiene información básica de la persona como nombre, apellido, DNI, email, teléfono y observaciones.
 */
public class PersonaDTO {
    @NotEmpty(message = "Se requiere nombre")
    private String nombre;

    @NotEmpty(message = "Se requiere apellido")
    private String apellido;

    @NotEmpty(message = "Se requiere DNI")
    private String dni;

    private String email;

    private String telefono;

    private String observaciones;

    public PersonaDTO() {
    }

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
