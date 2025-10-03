
package com.jesus24dev.carnetizacion.models;

import java.time.LocalDateTime;

public class Notification {
    private String type;
    private String message;
    private String entity;
    private String entityId;
    private LocalDateTime timestamp;
    private Object data;
    
    public Notification() {}
    
    public Notification(String type, String message, String entity, String entityId) {
        this.type = type;
        this.message = message;
        this.entity = entity;
        this.entityId = entityId;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    
}