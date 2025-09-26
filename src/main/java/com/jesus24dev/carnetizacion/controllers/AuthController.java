
package com.jesus24dev.carnetizacion.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")

public class AuthController {
    @GetMapping("/")
    public String helloWorld(){
        return "hello world";
    }
}
