package com.utn.SistemaDePersonas.exception;



/**
 * Excepción lanzada cuando un usuario no puede ser eliminada.
 */
public class UserNoEliminableException extends RuntimeException {
    public UserNoEliminableException(String message) {
        super(message);
    }
}
