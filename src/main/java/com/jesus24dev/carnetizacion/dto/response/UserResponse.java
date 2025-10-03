
package com.jesus24dev.carnetizacion.dto.response;

import com.jesus24dev.carnetizacion.models.User;

public class UserResponse {
    private final Long id;
    private final String username;
    private final User.Role role;
    private final boolean active;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.active = user.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }
   
}
