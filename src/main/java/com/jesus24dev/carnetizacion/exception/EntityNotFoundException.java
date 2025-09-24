
package com.jesus24dev.carnetizacion.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String id) {
        super("[" + entity + "] con ID: [" + id + "] no encontrado.");
    }
    
}
