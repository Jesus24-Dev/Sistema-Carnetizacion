
package com.jesus24dev.carnetizacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carnet")
public class CarnetController {
    @GetMapping()
    public String ciPage(){
        return "consultar_cedula";
    }
    
    @GetMapping("/data")
    public String requestData(){
        return "solicitar_datos";
    }
    
    @GetMapping("/data/image")
    public String requestImage(){
        return "subir_foto";
    }
    
    @GetMapping("/data/success")
    public String sucessMessage(){
        return "exito";
    }
}
