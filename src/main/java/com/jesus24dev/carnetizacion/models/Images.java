
package com.jesus24dev.carnetizacion.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pathFile;
    
    @OneToOne(mappedBy = "images")
    private Employee employee;
    
    @CreatedDate
    private LocalDate uploadedAt;
    

    public Images() {
    }

    public Images(Long id, String pathFile, Employee employee, LocalDate uploadedAt) {
        this.id = id;
        this.pathFile = pathFile;
        this.employee = employee;
        this.uploadedAt = uploadedAt;
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

    public LocalDate getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDate uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
   
}
