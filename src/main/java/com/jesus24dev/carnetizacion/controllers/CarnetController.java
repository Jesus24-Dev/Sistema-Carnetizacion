
package com.jesus24dev.carnetizacion.controllers;

import com.jesus24dev.carnetizacion.dto.request.EmployeeRequest;
import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/carnet")
public class CarnetController {
    
     private final EmployeeService employeeService;
    
    @Autowired
    public CarnetController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    
    @GetMapping()
    public String ciPage(){
        return "consultar_cedula";
    }
    
    @GetMapping("/data")
    public String requestData(@RequestParam("ci") String ci, Model model){
        model.addAttribute("ci", ci);
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
    
    @PostMapping("/create/requestCI")
    public String requestCiByCarnetRequest(@RequestParam("ci") String ci){
        boolean existsByCi = employeeService.existsByEmployeeCi(ci);
        
        if(!existsByCi){
            // En este caso, el usuario crea un nuevo registro "empleado" y llena los datos
            return "redirect:/carnet/data?ci=" + ci;
        } else {
            // El usuario ya tiene una solicitud pendiente, por lo que no necesita una nueva solicitud.
            return "redirect:/carnet";
        }
    }
    
    @PostMapping("/create/requestData")
    public String requestDataForCarnet(@ModelAttribute EmployeeRequest request){
        Employee employeeToSave = new Employee();
        employeeToSave.setCi(request.getCi());
        employeeToSave.setName(request.getName());
        employeeToSave.setLastname(request.getLastname());
        employeeToSave.setEmail(request.getEmail());
        employeeToSave.setGender(request.getGender());
        employeeToSave.setBirthday(request.getBirthday());

        Employee savedEmployee = employeeService.createEmployee(employeeToSave);
        
        return "redirect:/carnet/data/image?ci=" + savedEmployee.getCi();
    }
    
    @PostMapping("/create/requestImage")
    public String requestImageForCarnet(){
        return "hello world";
    }
    
}
