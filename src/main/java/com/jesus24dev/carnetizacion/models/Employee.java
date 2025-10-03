
package com.jesus24dev.carnetizacion.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    private String licenseStatus;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId", referencedColumnName = "id")
    private Images image;
    

    public Employee() {
    }

    public Employee(String ci, String name, String lastname, Gender gender, String email, LocalDate birthday, Images image, String licenseStatus) {
        this.ci = ci;
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.image = image;
        this.licenseStatus = licenseStatus;
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

    public Images getImage() {
        return image;
    }

    public void setImage(Images image) {
        this.image = image;
    }

    public String getLicenceStatus() {
        return licenseStatus;
    }

    public void setLicenceStatus(String licenceStatus) {
        this.licenseStatus = licenceStatus;
    }
    
    
    
     
    public enum Gender {
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


