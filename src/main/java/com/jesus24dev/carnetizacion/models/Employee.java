
package com.jesus24dev.carnetizacion.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    private String ci;
    private String name;
    private String lastname;
    private Gender gender;
    private String email;
    private LocalDate birthday;

    public Employee() {
    }

    public Employee(String ci, String name, String lastname, Gender gender, String email, LocalDate birthday) {
        this.ci = ci;
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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
     
    private enum Gender {
        M(1, "MALE"),
        F(2, "FEMALE");
        
        private final int number;
        private final String gender;
        
        Gender(int number, String gender){
            this.number = number;
            this.gender = gender;
        }
        
        public int getNumber(){
            return this.number;
        }
        
        public String getGender(){
            return this.gender;
        }
    }
}


