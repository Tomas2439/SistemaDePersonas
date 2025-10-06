package com.utn.SistemaDePersonas.service;


import com.utn.SistemaDePersonas.exception.DniAlreadyExistsException;
import com.utn.SistemaDePersonas.exception.PersonaNoEliminableException;
import com.utn.SistemaDePersonas.models.PersonaDTO;
import com.utn.SistemaDePersonas.models.PersonaModel;
import com.utn.SistemaDePersonas.models.VersionDTO;
import com.utn.SistemaDePersonas.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejar operaciones sobre la entidad Persona.
 * Incluye creación, edición, eliminación y consultas.
 */
@Service
public class PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    /**
     * Obtiene todas las personas registradas.
     *
     * @return Lista de personas
     */
    public List<PersonaModel> obtenerPersonas() {
        return  personaRepository.findAll();
    }

    /**
     * Guarda o actualiza una persona.
     *
     * @param persona entidad Persona
     * @return Persona guardada
     */
    public PersonaModel guardarPersona(PersonaModel persona) {
        return personaRepository.save(persona);
    }

    /**
     * Obtiene una persona por su ID.
     *
     * @param id ID de la persona
     * @return Optional con la persona o vacío si no existe
     */
    public Optional<PersonaModel> obtenerPorId(Long id) {
        return personaRepository.findById(id);
    }

    /**
     * Obtiene una lista de personas que coinciden con un DNI.
     *
     * @param dni DNI a buscar
     * @return Lista de personas encontradas
     */
    public List<PersonaModel> obtenerPorDni(String dni) {
        return personaRepository.findByDni(dni);
    }

    /**
     * Elimina una persona por ID.
     *
     * @param id ID de la persona
     * @throws PersonaNoEliminableException si ocurre un error al eliminar
     */
    public void eliminarPersona(Long id) {
        try {
            personaRepository.deleteById(id);
        } catch (Exception e) {
            throw new PersonaNoEliminableException(
                    "Error al eliminar la persona con ID " + id + ": " + e.getMessage()
            );
        }
    }//eliminarPersona()

    /**
     * Verifica si existe una persona con un determinado DNI.
     *
     * @param dni DNI a verificar
     * @return true si existe, false si no
     */
    public boolean existePorDni(String dni) {
        return personaRepository.existsByDni(dni);
    }

    /**
     * Devuelve la información de versión del sistema.
     *
     * @return DTO con versión del sistema
     */
    public VersionDTO getVersion() {
        return new VersionDTO(
                "Sistema de gestion de personas",
                "Kevin Sanchez, Tomas Stutz",
                "1.0.1",
                "2025/10/5"
        );
    }//getVersion()

    /**
     * Crea una nueva persona.
     *
     * @param dto DTO con los datos de la persona
     * @return Persona creada
     * @throws DniAlreadyExistsException si el DNI ya existe
     */
    public PersonaModel crearPersona(PersonaDTO dto) {
        if (this.existePorDni(dto.getDni())) {
            throw new DniAlreadyExistsException("El DNI ya existe");
        }

        PersonaModel persona = mapDTOtoPersona(dto);
        return personaRepository.save(persona);
    }//crearPersona()

    /**
     * Edita una persona existente.
     *
     * @param id         ID de la persona
     * @param personaDto DTO con los datos actualizados
     * @return Persona actualizada
     * @throws DniAlreadyExistsException si el DNI ya existe en otra persona
     * @throws IllegalArgumentException  si la persona no existe
     */
    public PersonaModel editarPersona(Long id, PersonaDTO personaDto) {
        PersonaModel persona = this.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        if (this.existePorDni(personaDto.getDni()) &&
                !personaDto.getDni().equals(persona.getDni())) {
            throw new DniAlreadyExistsException("El DNI ya existe");
        }

        persona.setNombre(personaDto.getNombre());
        persona.setApellido(personaDto.getApellido());
        persona.setDni(personaDto.getDni());
        persona.setEmail(personaDto.getEmail());
        persona.setTelefono(personaDto.getTelefono());
        persona.setObservaciones(personaDto.getObservaciones());

        return this.guardarPersona(persona);
    }//editarPersona()

    /**
     * Método privado para mapear PersonaDTO a PersonaModel
     *
     * @param dto PersonaDTO a mapear
     * @return PersonaModel con los datos
     */
    private PersonaModel mapDTOtoPersona(PersonaDTO dto) {
        PersonaModel persona = new PersonaModel();
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setDni(dto.getDni());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setObservaciones(dto.getObservaciones());
        return persona;
    }//mapDTOtoPersona()
}
