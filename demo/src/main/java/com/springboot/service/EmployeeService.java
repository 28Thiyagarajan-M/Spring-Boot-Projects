package com.springboot.service;

import com.springboot.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> getAllEmployee();

    Employee findById(String id);

    String deleteEmployee(String id);
}
