package com.springboot.service;

import com.springboot.entity.EmployeeEntity;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeV2ServiceImpl implements EmployeeService{


    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        if(employee.getEmployeeId() == null ||
                employee.getEmailId().isEmpty()) {
            employee.setEmployeeId(UUID.randomUUID().toString());
        }

        EmployeeEntity entity = new EmployeeEntity();
        BeanUtils.copyProperties(employee,entity);
        employeeRepository.save(entity);

        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<EmployeeEntity> employeeEntityList
                = employeeRepository.findAll();

        List<Employee> employees
                = employeeEntityList
                .stream()
                .map(employeeEntity -> {
                    Employee employee = new Employee();
                    BeanUtils.copyProperties(employeeEntity, employee);
                    return employee;
                })
                .collect(Collectors.toList());

        return employees;
    }


    @Override
    public Employee findById(String id) {
        EmployeeEntity ee = employeeRepository.findById(id).get();
        Employee e = new Employee();
        BeanUtils.copyProperties(ee,e);
        return e;
    }

    @Override
    public String deleteEmployee(String id) {
        employeeRepository.deleteById(id);
        return "Employees Removed Successfully Id:"+id;
    }
}
