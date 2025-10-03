
package com.jesus24dev.carnetizacion.services;

import com.jesus24dev.carnetizacion.exception.EntityNotFoundException;
import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.models.Images;
import com.jesus24dev.carnetizacion.repository.EmployeeRepository;
import com.jesus24dev.carnetizacion.repository.ImagesRepository;
import jakarta.transaction.Transactional;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImagesService {
    
    private ImagesRepository imagesRepository;
    private EmployeeRepository employeeRepository;
    
    @Autowired
    public ImagesService(ImagesRepository imagesRepository, EmployeeRepository employeeRepository){
        this.imagesRepository = imagesRepository;
        this.employeeRepository = employeeRepository;
    }
    
    public Images getImageByEmployee(Employee emp){
        Images image = imagesRepository.findByEmployee(emp)
                .orElseThrow(() -> new EntityNotFoundException("Imagen", emp.getCi()));
        
        return image;
    }
    
    public Images createImage(String filePath, String ci, boolean validated){
        Optional<Images> existingImage = imagesRepository.findByEmployeeCi(ci);
        Employee employee = employeeRepository.findById(ci).get();

        Images image;
        if (existingImage.isPresent()) {
            image = existingImage.get();
            image.setPathFile(filePath);
            image.setUploadedAt(LocalDate.now());
            image.setValidated(validated); // Nuevo campo
            employee.setImage(image);
        } else {
            image = new Images();
            image.setPathFile(filePath);
            image.setEmployee(employee);
            image.setUploadedAt(LocalDate.now());
            image.setValidated(validated); // Nuevo campo
            employee.setImage(image);
        }

        return imagesRepository.save(image);
    }
    
    public String getImagePath(String ci){
        Employee employeeFounded = employeeRepository.findById(ci).get();  
        String pathFile = employeeFounded.getImage().getPathFile();       
        return pathFile;
    }
    
     public void deleteImageByCi(String ci) {
        imagesRepository.deleteByEmployeeCi(ci);
    }
     
     public boolean existsByCi(String ci) {
        return employeeRepository.existsByCi(ci);
    }
}
