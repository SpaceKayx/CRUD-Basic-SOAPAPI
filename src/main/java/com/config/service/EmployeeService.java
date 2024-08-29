package com.config.service;

import com.config.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> selectAllEmployee();

    Employee selectEmployeeById(long id);

    void createEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(long id);
}
