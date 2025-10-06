package com.utn.SistemaDePersonas.service;


import com.utn.SistemaDePersonas.exception.PersonaNoEliminableException;
import com.utn.SistemaDePersonas.exception.UserNoEliminableException;
import com.utn.SistemaDePersonas.exception.UsernameAlreadyExistsException;
import com.utn.SistemaDePersonas.models.*;
import com.utn.SistemaDePersonas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para manejar operaciones sobre la entidad Usuario.
 * Incluye creación, edición, eliminación, verificación de existencia y cambios de prioridad.
 */
@Service
public class UsuarioService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonaService personaService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    public Optional<User> obtenerPorId(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Obtiene un usuario por su username.
     *
     * @param username nombre de usuario
     * @return Usuario encontrado o null si no existe
     */
    public User obtenerPorUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Verifica si existe un usuario con un determinado username.
     *
     * @param username nombre de usuario
     * @return true si existe, false si no
     */
    public boolean existePorUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Guarda o actualiza un usuario en la base de datos.
     *
     * @param usuario entidad usuario a guardar
     * @return Usuario guardado
     */
    public User guardarUsuario(User usuario) {
        return userRepository.save(usuario);
    }

    /**
     * Elimina un usuario por ID.
     *
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si ocurrió algún error
     */
    public void eliminarUsuario(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserNoEliminableException("Error al eliminar el usuario: " + e.getMessage());
        }
    }//eliminarUsuario()

    /**
     * Verifica si existe al menos un usuario registrado.
     *
     * @return true si hay algún usuario, false si no
     */
    public boolean existeAlgunUsuario() {
        return userRepository.count() > 0;
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param dto DTO con los datos del usuario a crear
     * @return Usuario creado
     * @throws UsernameAlreadyExistsException si el username ya existe
     * @throws IllegalArgumentException       si la persona asociada no existe
     */
    public User registrarUsuario(UsuarioDTO dto) {
        boolean primerUsuario = !this.existeAlgunUsuario();

        if (this.existePorUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
        }

        PersonaModel persona = personaService.obtenerPorId(dto.getPersonaId())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        User usuario = new User();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setPriority(primerUsuario ? 1 : dto.getPriority());
        usuario.setPersona(persona);

        return this.guardarUsuario(usuario);
    }//registrarUsuario()

    /**
     * Cambia la prioridad/admin de un usuario (toggle).
     *
     * @param id ID del usuario
     * @throws IllegalArgumentException si el usuario no existe
     */
    public void toggleAdmin(Long id) {
        User usuario = this.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setPriority(usuario.getPriority() == 1 ? 0 : 1);
        this.guardarUsuario(usuario);
    }//toggleAdmin()

    /**
     * Devuelve el ID de la persona asociada a un usuario.
     *
     * @param id ID del usuario
     * @return ID de la persona
     * @throws IllegalArgumentException si el usuario no existe
     */
    public Long devolverIdPersona(Long id) {
        User usuario = this.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return usuario.getPersona().getId();
    }//devolverIdPersona()

    /**
     * Edita la información de un usuario existente.
     *
     * @param usuarioDto      DTO con los datos actualizados
     * @param usuarioLogueado usuario actualmente logueado
     * @return Usuario actualizado
     * @throws UsernameAlreadyExistsException si el username ya existe para otro usuario
     */
    public User editarUsuario(UsuarioEditDTO usuarioDto, SecurityUser usuarioLogueado) {
        if (this.existePorUsername(usuarioDto.getUsername()) &&
                !usuarioDto.getUsername().equals(usuarioLogueado.getUsername())) {
            throw new UsernameAlreadyExistsException("El nombre de usuario ya existe");
        }

        User user = usuarioLogueado.getUser();
        user.setUsername(usuarioDto.getUsername());

        if (usuarioDto.getPassword() != null && !usuarioDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        }

        return this.guardarUsuario(user);
    }//editarUsuario()
}

