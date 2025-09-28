
package com.jesus24dev.carnetizacion.controllers;

import com.jesus24dev.carnetizacion.dto.request.UserRequest;
import com.jesus24dev.carnetizacion.dto.response.UserResponse;
import com.jesus24dev.carnetizacion.models.User;
import com.jesus24dev.carnetizacion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        User user = new User();
        
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setActive(true);
        
        User userCreated = userService.createUser(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(userCreated));
    }
}
