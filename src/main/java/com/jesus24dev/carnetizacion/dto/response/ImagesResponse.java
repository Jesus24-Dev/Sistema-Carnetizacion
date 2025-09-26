package com.jesus24dev.carnetizacion.dto.response;

import com.jesus24dev.carnetizacion.models.Images;
import java.time.LocalDate;


public class ImagesResponse {
    private Long id;
    private String pathFile;
    private String employeeId;
    private LocalDate uploadedAt;
    
    public ImagesResponse(Images image){
        this.id = image.getId();
        this.pathFile = image.getPathFile();
        this.employeeId = image.getEmployee().getCi();
        this.uploadedAt = image.getUploadedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDate uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
    
    
}
