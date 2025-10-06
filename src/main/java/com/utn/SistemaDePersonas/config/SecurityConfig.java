package com.utn.SistemaDePersonas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Utiliza Spring Security para definir las reglas de acceso,
 * login, logout, manejo de excepciones y codificación de contraseñas.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad.
     * Define las reglas de autorización de endpoints, login, logout y páginas de error.
     *
     * @param http objeto de configuración de seguridad de Spring
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilita CSRF (recomendado habilitar en producción para proteger formularios)
                .csrf(AbstractHttpConfigurer::disable)

                // Configuración de autorización de endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/public/**").permitAll() // acceso libre a recursos públicos
                        .requestMatchers("/persona/agregar").permitAll() // permitir agregar personas (para primer usuario)
                        .requestMatchers("/private/**").hasAuthority("ADMIN") // solo accesible a administradores
                        .anyRequest().authenticated() // cualquier otro endpoint requiere autenticación
                )

                // Configuración de login
                .formLogin(form -> form
                        .loginPage("/public/login")                  // página personalizada de login
                        .defaultSuccessUrl("/public/home", true)     // redirige al home tras login exitoso
                        .permitAll()                                 // permite acceso a todos al login
                )

                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/public/logout")                 // endpoint de logout
                        .logoutSuccessUrl("/public/home")            // redirige al home tras logout
                        .permitAll()
                )

                // Página personalizada para acceso denegado
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"))

                .build();
    }//securityFilterChain()

    /**
     * Bean que define el codificador de contraseñas.
     * Se utiliza BCrypt, un algoritmo seguro de hashing para almacenar contraseñas.
     *
     * @return PasswordEncoder configurado con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


