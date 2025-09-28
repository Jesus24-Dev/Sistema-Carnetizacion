package com.jesus24dev.carnetizacion.services;

import com.jesus24dev.carnetizacion.exception.EntityNotFoundException;
import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.models.Images;
import com.jesus24dev.carnetizacion.repository.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final NotificationService notificationService;
    
    
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, NotificationService notificationService){
        this.employeeRepository = employeeRepository;
        this.notificationService = notificationService;
    }
    
    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }
    
    public Employee findEmployeeByCi(String ci){
        Employee employeeFounded = employeeRepository.findById(ci)
                .orElseThrow(() -> new EntityNotFoundException("Empleado", ci));
        
        return employeeFounded;
    }
    
    public Employee createEmployee(Employee employee){
        notificationService.notifyNewEmployee(employee);
        
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Employee employee, String ci){
        Employee employeeFounded = this.findEmployeeByCi(ci);
        employeeFounded.setName(employee.getName());
        employeeFounded.setLastname(employee.getLastname());
        employeeFounded.setEmail(employee.getEmail());
        
        return employeeRepository.save(employeeFounded);
    }
    
    public void deleteEmployee(String ci){
        Employee employeeFounded = this.findEmployeeByCi(ci);
        employeeRepository.delete(employeeFounded);
    }
    
    public void updateImage(Images image, Employee employee){       
        employee.setImage(image);
        employeeRepository.save(employee);
    }

    public boolean existsByEmployeeCi(String ci) {
        return employeeRepository.existsByCi(ci);
    }
    
}
