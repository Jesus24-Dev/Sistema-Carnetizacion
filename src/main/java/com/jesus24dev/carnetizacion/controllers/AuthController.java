
package com.jesus24dev.carnetizacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {
    @GetMapping
    public String index(){
        return "index";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
