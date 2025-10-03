
package com.jesus24dev.carnetizacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AuthController {
    @GetMapping("/")
    public String home(@RequestParam(value = "ci", required = false) String ci, Model model) {
        if (ci != null && !ci.trim().isEmpty()) {
            // Lógica para buscar estado del carnet
            //TODO hacer servicio para verificar estado de solicitud.
//            String estado = carnetService.obtenerEstadoCarnet(ci);
            model.addAttribute("carnetStatus", "APROBADO");
        }
        return "index";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
