package com.utn.SistemaDePersonas.exception;

/**
 * Excepción lanzada cuando el username ya existe.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}