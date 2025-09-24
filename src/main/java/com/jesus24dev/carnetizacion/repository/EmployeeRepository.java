
package com.jesus24dev.carnetizacion.repository;

import com.jesus24dev.carnetizacion.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
  
}
