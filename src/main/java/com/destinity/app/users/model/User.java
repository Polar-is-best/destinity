package com.destinity.app.users.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class User {

    private ObjectId id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    private String role;
    private String status;
    private EmployeeData employeeData;
    private ProviderData providerData;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(
            String firstName,
            String lastName,
            String middleName,
            String email,
            String password,
            String role,
            String status,
            EmployeeData employeeData,
            ProviderData providerData) {
        this.id = new ObjectId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.employeeData = employeeData;
        this.providerData = providerData;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }
    
    public void updateModificationDate(){
        this.updatedAt = LocalDateTime.now();
    }
}
