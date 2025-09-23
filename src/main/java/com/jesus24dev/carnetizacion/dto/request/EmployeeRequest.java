
package com.jesus24dev.carnetizacion.dto.request;

import com.jesus24dev.carnetizacion.models.Employee;
import java.time.LocalDate;

public class EmployeeRequest {
    private String ci;
    private String name;
    private String lastname;
    private Employee.Gender gender;
    private String email;
    private LocalDate birthday;
  
    public EmployeeRequest(Employee employee) {
        this.ci = employee.getCi();
        this.name = employee.getName();
        this.lastname = employee.getLastname();
        this.gender = employee.getGender();
        this.email = employee.getEmail();
        this.birthday = employee.getBirthday();
    }

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
