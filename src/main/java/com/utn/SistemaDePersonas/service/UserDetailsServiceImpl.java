package com.utn.SistemaDePersonas.service;


import com.utn.SistemaDePersonas.models.SecurityUser;
import com.utn.SistemaDePersonas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link UserDetailsService} para la integración con Spring Security.
 * Se encarga de cargar los detalles de un usuario a partir de su username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Carga los detalles de un usuario dado su username.
     *
     * @param username nombre de usuario a buscar
     * @return UserDetails del usuario encontrado
     * @throws UsernameNotFoundException si no existe un usuario con el username proporcionado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usuarioService.obtenerPorUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new SecurityUser(user);
    }//loadUserByUsername()
}
