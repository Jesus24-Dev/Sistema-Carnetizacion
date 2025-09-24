package com.jesus24dev.carnetizacion.services;

import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.repository.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    
    private EmployeeRepository employeeRepository;
    
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    
    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }
    
    public Employee findEmployeeByCi(String ci){
        Employee employeeFounded = employeeRepository.findById(ci)
                .orElseThrow(() -> new IllegalArgumentException("Employee not founded"));
        
        return employeeFounded;
    }
    
    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Employee employee, String ci){
        Employee employeeFounded = employeeRepository.findById(ci)
                .orElseThrow(() -> new IllegalArgumentException("Employee not founded"));
        
        employeeFounded.setName(employee.getName());
        employeeFounded.setLastname(employee.getLastname());
        employeeFounded.setEmail(employee.getEmail());
        
        return employeeRepository.save(employeeFounded);
    }
    
    public void deleteEmployee(String ci){
        Employee employeeFounded = employeeRepository.findById(ci)
                .orElseThrow(() -> new IllegalArgumentException("Employee not founded"));
        employeeRepository.delete(employeeFounded);
    }
}
