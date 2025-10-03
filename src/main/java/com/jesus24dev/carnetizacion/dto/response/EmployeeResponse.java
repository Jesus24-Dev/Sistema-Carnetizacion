
package com.jesus24dev.carnetizacion.dto.response;

import com.jesus24dev.carnetizacion.models.Employee;
import java.time.LocalDate;

public class EmployeeResponse {
    private final String ci;
    private final String name;
    private final String lastname;
    private final Employee.Gender gender;
    private final String email;
    private final LocalDate birthday;
    private final Long imageId;
    private final String licenseStatus;
    
    public EmployeeResponse(Employee employee) {
        this.ci = employee.getCi();
        this.name = employee.getName();
        this.lastname = employee.getLastname();
        this.gender = employee.getGender();
        this.email = employee.getEmail();
        this.birthday = employee.getBirthday();
        this.imageId = (employee.getImage() != null) ? employee.getImage().getId() : null;
        this.licenseStatus = employee.getLicenceStatus();
    }

    public String getCi() {
        return ci;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Employee.Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }
    
    
}
