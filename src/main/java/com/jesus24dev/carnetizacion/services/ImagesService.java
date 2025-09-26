
package com.jesus24dev.carnetizacion.services;

import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.models.Images;
import com.jesus24dev.carnetizacion.repository.ImagesRepository;
import java.nio.file.Path;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesService {
    
    private ImagesRepository imagesRepository;
    private EmployeeService employeeService;
    
    @Autowired
    public ImagesService(ImagesRepository imagesRepository, EmployeeService employeeService){
        this.imagesRepository = imagesRepository;
        this.employeeService = employeeService;
    }
    
    public Images createImage(Path path, String ci){
        Images image = new Images();
        Employee employeeFounded = employeeService.findEmployeeByCi(ci);
        
        image.setPathFile(path.toString());
        image.setEmployee(employeeFounded);
        image.setUploadedAt(LocalDate.now());
        
        employeeFounded.setImage(image);
        
        employeeService.updateImage(image, employeeFounded);
        imagesRepository.save(image);
        
        return image;
    }
    
    public String getImagePath(String ci){
        Employee employeeFounded = employeeService.findEmployeeByCi(ci);
        
        String pathFile = employeeFounded.getImage().getPathFile();
        
        return pathFile;
    }
}
