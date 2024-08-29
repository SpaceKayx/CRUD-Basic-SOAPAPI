package com.config.service.implement;

import com.config.model.Employee;
import com.config.repository.EmployeeRepository;
import com.config.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeImplement implements EmployeeService {

    @Autowired private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> selectAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee selectEmployeeById(long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {

        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {

        employeeRepository.deleteById(id);
    }
}
