
package com.jesus24dev.carnetizacion.dto.request;

import com.jesus24dev.carnetizacion.models.User;

public class UserRequest {
    private String username;
    private String password;
    private User.Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
   
}
