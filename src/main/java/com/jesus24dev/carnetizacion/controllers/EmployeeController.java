
package com.jesus24dev.carnetizacion.controllers;

import com.jesus24dev.carnetizacion.dto.request.EmployeeRequest;
import com.jesus24dev.carnetizacion.dto.request.EmployeeUpdateRequest;
import com.jesus24dev.carnetizacion.dto.response.EmployeeResponse;
import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.services.EmployeeService;
import com.jesus24dev.carnetizacion.services.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    
    
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;

    }
    
    @GetMapping()
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(){
        List<Employee> employees = employeeService.findAllEmployees();
        
        return ResponseEntity.ok(employees.stream().map(EmployeeResponse::new).toList());
    }
    
    @GetMapping("/{ci}")
    public ResponseEntity<EmployeeResponse> getEmployeeByCi(@PathVariable String ci){
        Employee employeeFounded = employeeService.findEmployeeByCi(ci);
        
        return ResponseEntity.ok(new EmployeeResponse(employeeFounded));
    }
    
    @PostMapping()
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest request){
        Employee employeeToSave = new Employee();
        employeeToSave.setCi(request.getCi());
        employeeToSave.setName(request.getName());
        employeeToSave.setLastname(request.getLastname());
        employeeToSave.setEmail(request.getEmail());
        employeeToSave.setGender(request.getGender());
        employeeToSave.setBirthday(request.getBirthday());

        Employee savedEmployee = employeeService.createEmployee(employeeToSave);

        EmployeeResponse response = new EmployeeResponse(savedEmployee);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{ci}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String ci, @RequestBody @Valid EmployeeUpdateRequest request){
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setLastname(request.getLastname());
        employee.setEmail(request.getEmail());
        Employee updatedEmployee = employeeService.updateEmployee(employee, ci);
        
        EmployeeResponse response = new EmployeeResponse(updatedEmployee);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{ci}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String ci){
        employeeService.deleteEmployee(ci);
        
        return ResponseEntity.noContent().build();
    }
}
