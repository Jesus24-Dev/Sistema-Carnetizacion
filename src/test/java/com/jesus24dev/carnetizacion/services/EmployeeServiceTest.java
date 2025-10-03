
package com.jesus24dev.carnetizacion.services;

import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.repository.EmployeeRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    
    @Mock
    EmployeeRepository employeeRepository;
    
    @InjectMocks
    EmployeeService employeeService;
    
    @Test
    void shoultReturnEmployeeWhenIdExists(){
        Employee mockEmployee = new Employee("101010", "Juan", "Gutierrez", Employee.Gender.M, "juanjo@gmail.com", LocalDate.of(2004, Month.APRIL, 4), null);
    
        when(employeeRepository.findById("101010")).thenReturn(Optional.of(mockEmployee));
        
        Employee employeeResult = employeeService.findEmployeeByCi("101010");
        
        assertNotNull(employeeResult);       
        assertEquals("Juan", employeeResult.getName());
        assertEquals("Gutierrez", employeeResult.getLastname());        
        assertEquals("juanjo@gmail.com", employeeResult.getEmail());
        
        verify(employeeRepository).findById("101010");
    }
}
