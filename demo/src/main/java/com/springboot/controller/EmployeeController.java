package com.springboot.controller;

import com.springboot.model.Employee;
import com.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/employee")
public class EmployeeController {

    @Qualifier("employeeServiceImpl")
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("save")
    public Employee save(@RequestBody Employee emp) {
        employeeService.save(emp);
        return emp;
    }

    @GetMapping("getAll")
    public List<Employee> getAll(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("get/{id}")
    public Employee getById(@PathVariable String id){
        return employeeService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id){
        return employeeService.deleteEmployee(id);
    }


}
