package com.springboot.service;

import com.springboot.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    List<Employee> employees = new ArrayList<>();

    @Override
    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(UUID.randomUUID().toString());

        }

        employees.add(employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employees;
    }

    @Override
    public Employee findById(String id) {
        return employees.stream().filter(employee -> employee.getEmailId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Not Found With ID: "+ id));

    }

    @Override
    public String deleteEmployee(String id) {
        Employee e = employees.stream().filter(em-> em.getEmployeeId().equalsIgnoreCase(id)).findFirst().get();;
        employees.remove(e);
        return "Employees Removed Successfully Id:"+id;
    }


}
