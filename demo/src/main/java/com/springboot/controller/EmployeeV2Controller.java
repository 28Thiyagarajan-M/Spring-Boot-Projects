package com.springboot.controller;

import com.springboot.model.Employee;
import com.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v2/employee")
public class EmployeeV2Controller {

    @Qualifier("employeeV2ServiceImpl")
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("save")
    public Employee save(@RequestBody Employee emp) {
        employeeService.save(emp);
        return emp;
    }

    @GetMapping("/getAll")
    public List<Employee> getAll(){
        return employeeService.getAllEmployee();
    }


    @GetMapping("/{id}")
    public Employee get(@PathVariable String id){
        return employeeService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String del(@PathVariable String id){
        return employeeService.deleteEmployee(id);
    }


}

