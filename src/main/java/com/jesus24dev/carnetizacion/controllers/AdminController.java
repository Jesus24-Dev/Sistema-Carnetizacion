
package com.jesus24dev.carnetizacion.controllers;

import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.services.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public EmployeeService employeeService;

    @Autowired
    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
     
    @GetMapping()
    public String home(Model model){
        List<Employee> employees = employeeService.findAllEmployees();
        
        model.addAttribute("employees", employees);
        return "admin/home";
    }
}
