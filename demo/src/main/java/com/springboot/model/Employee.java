package com.springboot.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String department;

}


