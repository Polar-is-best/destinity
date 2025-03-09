package com.destinity.app.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeData {

    private String position;
    private String department;
    private double salary;
    private String hireDate;
}
