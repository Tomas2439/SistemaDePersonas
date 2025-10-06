package com.utn.SistemaDePersonas.exception;

/**
 * Excepción lanzada cuando una persona no puede ser eliminada.
 */
public class PersonaNoEliminableException extends RuntimeException {
    public PersonaNoEliminableException(String message) {
        super(message);
    }
}
