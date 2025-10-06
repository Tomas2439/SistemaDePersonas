package com.utn.SistemaDePersonas.repository;

import com.utn.SistemaDePersonas.models.PersonaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Repositorio para la entidad Persona.
 * Permite consultas básicas y búsqueda por DNI.
 */
@Repository
public interface PersonaRepository extends JpaRepository<PersonaModel, Long> {
    public abstract ArrayList<PersonaModel> findByDni(String dni);

    boolean existsByDni(String dni);
}
