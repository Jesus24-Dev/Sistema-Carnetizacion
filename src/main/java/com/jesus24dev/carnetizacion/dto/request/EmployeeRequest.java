
package com.jesus24dev.carnetizacion.dto.request;

import com.jesus24dev.carnetizacion.models.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class EmployeeRequest {
    @NotBlank(message = "La cedula del empleado no puede estar vacia.")
    private String ci;
    
    @NotBlank(message = "El nombre no puede estar vacio.")
    private String name;
    
    @NotBlank(message = "El apellido no puede estar vacio.")
    private String lastname;
    private Employee.Gender gender;
    
    @NotBlank(message = "El email no puede estar vacio.")
    @Email(message = "Ingrese un formato correcto para el correo electronico.")
    private String email;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    private LocalDate birthday;

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Employee.Gender getGender() {
        return gender;
    }

    public void setGender(Employee.Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
       
}
