
package com.jesus24dev.carnetizacion.controllers;

import com.jesus24dev.carnetizacion.dto.request.EmployeeRequest;
import com.jesus24dev.carnetizacion.models.Employee;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carnet")
public class CarnetController {
    
    private final EmployeeService employeeService;
    private final ImageValidationService imageValidationService;
    private final ImagesService imagesService;
    private final String UPLOAD_DIR = "src/main/resources/static/images/";
    
    @Autowired
    public CarnetController(EmployeeService employeeService, ImageValidationService imageValidationService, ImagesService imagesService){
        this.employeeService = employeeService;
        this.imageValidationService = imageValidationService;
        this.imagesService = imagesService;
    }
    
    @GetMapping()
    public String ciPage(){
        return "consultar_cedula";
    }
    
    @GetMapping("/data")
    public String requestData(@RequestParam("ci") String ci, Model model){
        model.addAttribute("ci", ci);
        return "solicitar_datos";
    }
    
    @GetMapping("/data/image")
    public String requestImage(){
        return "subir_foto";
    }
    
    @GetMapping("/data/success")
    public String sucessMessage(){
        return "exito";
    }
    
    @PostMapping("/create/requestCI")
    public String requestCiByCarnetRequest(@RequestParam("ci") String ci, RedirectAttributes redirectAttributes){
        boolean existsByCi = employeeService.existsByEmployeeCi(ci);
        
        if(!existsByCi){
            // En este caso, el usuario crea un nuevo registro "empleado" y llena los datos
            return "redirect:/carnet/data?ci=" + ci;
        } else {
            // El usuario ya tiene una solicitud pendiente, por lo que no necesita una nueva solicitud.
            return "redirect:/carnet?exists=true";
        }
    }
    
    @PostMapping("/create/requestData")
    public String requestDataForCarnet(@ModelAttribute EmployeeRequest request, RedirectAttributes redirectAttributes){
        Employee employeeToSave = new Employee();
        employeeToSave.setCi(request.getCi());
        employeeToSave.setName(request.getName());
        employeeToSave.setLastname(request.getLastname());
        employeeToSave.setEmail(request.getEmail());
        employeeToSave.setGender(request.getGender());
        employeeToSave.setBirthday(request.getBirthday());

        Employee savedEmployee = employeeService.createEmployee(employeeToSave);
        redirectAttributes.addFlashAttribute("ci", savedEmployee.getCi());
        
        return "redirect:/carnet/data/image";
    }
    
    @PostMapping("/{ci}/uploadImage")
    public String uploadImage(
            @RequestParam("image") MultipartFile file,
            @PathVariable String ci,
            RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El archivo está vacío");
                return "redirect:/carnet/data/image?ci=" + ci;
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                redirectAttributes.addFlashAttribute("error", "Solo se permiten archivos de imagen");
                return "redirect:/carnet/data/image?ci=" + ci;
            }

            // Validación personalizada
            ImageValidationService.ValidationResult validationResult = imageValidationService.validateImage(file);
            if (!validationResult.isValid()) {
                redirectAttributes.addFlashAttribute("error", "La imagen no cumple los requisitos: " + validationResult.getMessage());
                return "redirect:/carnet/data/image?ci=" + ci;
            }

            // Guardado
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

            Images image = imagesService.createImage(("\\images\\" + fileName), ci, true);

            redirectAttributes.addFlashAttribute("success", "Imagen subida correctamente");
            redirectAttributes.addFlashAttribute("imagePath", image.getPathFile());

            return "redirect:/carnet/data/success?ci=" + ci;

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al subir la imagen: " + e.getMessage());
            return "redirect:/carnet/data/image?ci=" + ci;
        }
    }
    
}
