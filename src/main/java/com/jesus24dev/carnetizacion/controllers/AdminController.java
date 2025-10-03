
package com.jesus24dev.carnetizacion.controllers;

import com.jesus24dev.carnetizacion.dto.response.EmployeeResponse;
import com.jesus24dev.carnetizacion.dto.response.ImagesResponse;
import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.models.Images;
import com.jesus24dev.carnetizacion.services.EmployeeService;
import com.jesus24dev.carnetizacion.services.ImagesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public EmployeeService employeeService;
    public ImagesService imagesService;

    @Autowired
    public AdminController(EmployeeService employeeService, ImagesService imagesService) {
        this.employeeService = employeeService;
        this.imagesService = imagesService;
    }
     
    @GetMapping()
    public String home(Model model){
        List<Employee> employees = employeeService.findAllEmployees();
        
        List<EmployeeResponse> response = employees.stream().map(EmployeeResponse::new).toList();
        model.addAttribute("employees", response);
        return "admin/home";
    }
    
    @GetMapping("/employee/{ci}")
    public String employeeDetails(@PathVariable String ci, Model model){
        Employee employee = employeeService.findEmployeeByCi(ci);
        EmployeeResponse response = new EmployeeResponse(employee);
        
        Images image = imagesService.getImageByEmployee(employee);
        ImagesResponse imgResponse = new ImagesResponse(image);
        
        model.addAttribute("employee", response);
        model.addAttribute("image", imgResponse);
        
        return "admin/employee_details";
    }
}
