package com.utn.SistemaDePersonas.repository;

import com.utn.SistemaDePersonas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Usuario.
 * Permite consultas básicas y búsqueda por username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
