package com.jesus24dev.carnetizacion.controllers;

import com.jesus24dev.carnetizacion.dto.response.ImagesResponse;
import com.jesus24dev.carnetizacion.models.Images;
import com.jesus24dev.carnetizacion.services.EmployeeService;
import com.jesus24dev.carnetizacion.services.ImageValidationService;
import com.jesus24dev.carnetizacion.services.ImagesService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final String UPLOAD_DIR = "src/main/resources/static/images/";
    private final ImagesService imagesService;
    private final ImageValidationService imageValidationService;
    private final EmployeeService employeeService;
    
    @Autowired
    public ImageController(EmployeeService employeeService, ImagesService imagesService, ImageValidationService imageValidationService) {
        
        this.employeeService = employeeService;
        this.imagesService = imagesService;
        this.imageValidationService = imageValidationService;
        
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de imagenes", e);
        }
    }
     
    @PostMapping("/upload/{ci}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable String ci) {
        try {          
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("Solo se permiten archivos de imagen");
            }
            
            if (!imagesService.existsByCi(ci)) {
                return ResponseEntity.badRequest()
                    .body("No existe un empleado con CI: " + ci);
            }
            
            ImageValidationService.ValidationResult validationResult = 
            imageValidationService.validateImage(file);
            
            if (!validationResult.isValid()) {
                return ResponseEntity.badRequest()
                    .body("La imagen no cumple los requisitos: " + validationResult.getMessage());
            }

            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            
            String fileName = ci + fileExtension;
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            Images image = imagesService.createImage(("\\images" + fileName), ci, true);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ImagesResponse(image));
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }
    
    @GetMapping("/{ci}")
    public ResponseEntity<byte[]> getImage(@PathVariable String ci) {
        try {
            String imagePath = imagesService.getImagePath(ci);
            
            Path filePath = Paths.get(imagePath);
            
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(filePath);
            
            // Determinar el tipo de contenido
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                    .body(imageBytes);
                    
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{ci}")
    public ResponseEntity<?> deleteImage(@PathVariable String ci) {
        try {
            String imagePath = imagesService.getImagePath(ci);
            
            if (imagePath != null) {
                Path filePath = Paths.get(imagePath);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }
            
            imagesService.deleteImageByCi(ci);
            
            return ResponseEntity.ok().body("Imagen eliminada correctamente");
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la imagen: " + e.getMessage());
        }
    }   
}
