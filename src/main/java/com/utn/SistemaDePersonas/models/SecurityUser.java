package com.utn.SistemaDePersonas.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


/**
 * Clase de seguridad que implementa UserDetails para la integración con Spring Security.
 * Envuelve la entidad User y proporciona roles según la prioridad.
 */
public class SecurityUser implements UserDetails {
    private User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority sga;
        if (user.getPriority() > 0) {
            sga = new SimpleGrantedAuthority("ADMIN");
        } else {
            sga = new SimpleGrantedAuthority("USER");
        }
        return Collections.singletonList(sga);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Long getId() {
        return user.getId();
    }

    public Integer getPriority() {
        return user.getPriority();
    }

    public PersonaModel getPersona() {
        return user.getPersona();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}

