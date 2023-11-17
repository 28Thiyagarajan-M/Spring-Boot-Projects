package com.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeEntity {
    @Id
    private String employeeId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String department;
}
