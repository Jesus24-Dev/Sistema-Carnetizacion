
package com.jesus24dev.carnetizacion.services;

import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.models.Images;
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
    private EmployeeService employeeService;
    
    @Autowired
    public ImagesService(ImagesRepository imagesRepository, EmployeeService employeeService){
        this.imagesRepository = imagesRepository;
        this.employeeService = employeeService;
    }
    
    public Images createImage(String filePath, String ci, boolean validated){
        Optional<Images> existingImage = imagesRepository.findByEmployeeCi(ci);
        Employee employee = employeeService.findEmployeeByCi(ci);

        Images image;
        if (existingImage.isPresent()) {
            image = existingImage.get();
            image.setPathFile(filePath);
            image.setUploadedAt(LocalDate.now());
            image.setValidated(validated); // Nuevo campo
        } else {
            image = new Images();
            image.setPathFile(filePath);
            image.setEmployee(employee);
            image.setUploadedAt(LocalDate.now());
            image.setValidated(validated); // Nuevo campo
        }
        
        return imagesRepository.save(image);
    }
    
    public String getImagePath(String ci){
        Employee employeeFounded = employeeService.findEmployeeByCi(ci);  
        String pathFile = employeeFounded.getImage().getPathFile();       
        return pathFile;
    }
    
     public void deleteImageByCi(String ci) {
        imagesRepository.deleteByEmployeeCi(ci);
    }
     
     public boolean existsByCi(String ci) {
        return employeeService.existsByEmployeeCi(ci);
    }
}
