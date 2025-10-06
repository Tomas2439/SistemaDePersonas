package com.utn.SistemaDePersonas.models;

import com.utn.SistemaDePersonas.utils.Stringifiable;
import jakarta.persistence.*;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad PersonaModel que representa a una persona en la base de datos.
 * Relacionada con la entidad User mediante una relación uno-a-muchos.
 */
@Entity
@Table(name = "personas")
public class PersonaModel implements Stringifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String dni;

    private String email;
    private String telefono;
    private String observaciones;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("priority DESC, username ASC")
    private Set<User> usuarios = new HashSet<>();

    // Constructores
    public PersonaModel() {
    }

    public PersonaModel(String nombre, String apellido, String dni, String email, String telefono) {
        setNombre(nombre);
        setApellido(apellido);
        setDni(dni);
        setEmail(email);
        setTelefono(telefono);
    }

    // Getters y setters
    public Set<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<User> usuarios) {
        this.usuarios = usuarios;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

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

    /**
     * Muestra información de la persona en un PrintStream.
     *
     * @param oStream Stream donde se imprime la información
     */
    public void showOn(PrintStream oStream) {
        oStream.println("Nombre: " + this.getNombre());
        oStream.println("DNI: " + this.getDni());
        oStream.println("Domicilio: " + this.getEmail());
        oStream.println("Teléfono: " + this.getTelefono());
    }

    @Override
    public String toString() {
        return toStringRepresentation();
    }

    String esUn() {
        return "Persona";
    }
}