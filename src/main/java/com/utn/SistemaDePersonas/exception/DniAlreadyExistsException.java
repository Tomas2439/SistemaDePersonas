package com.utn.SistemaDePersonas.exception;

/**
 * Excepción lanzada cuando el DNI ya existe en el sistema.
 */
public class DniAlreadyExistsException extends RuntimeException {
    public DniAlreadyExistsException(String message) {
        super(message);
    }
}