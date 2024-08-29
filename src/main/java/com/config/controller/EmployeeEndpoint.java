package com.config.controller;

import com.config.service.implement.EmployeeImplement;
import com.config.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import allapis.config.com.*;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class EmployeeEndpoint {

    @Autowired
    private EmployeeImplement employeeService;

    private static final String NAMESPACE_URI = "http://com.config.allapis";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
    @ResponsePayload
    public AddEmployeeResponse addEmployee(@RequestPayload AddEmployeeRequest request) {
        AddEmployeeResponse response = new AddEmployeeResponse();
        ServiceStatus serverStatus = new ServiceStatus();
        Employee employee = new Employee();
        BeanUtils.copyProperties(request.getEmployeeInfo(), employee);
        employeeService.createEmployee(employee);
        serverStatus.setStatus("Success");
        serverStatus.setMessage("Employee added successfully");
        response.setServiceStatus(serverStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListEmployeeRequest")
    @ResponsePayload
    public GetListEmployeeResponse getListEmployee() {
        GetListEmployeeResponse response = new GetListEmployeeResponse();

        List<EmployeeInfo> employees = employeeService.selectAllEmployee().stream()
                .map(employee -> {
                    EmployeeInfo employeeInfo = new EmployeeInfo();
                    BeanUtils.copyProperties(employee, employeeInfo);
                    return employeeInfo;
                })
                .collect(Collectors.toList());

        response.getEmployeeInfo().addAll(employees);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeIdRequest")
    @ResponsePayload
    public GetEmployeeResponse getEmployeeId(@RequestPayload GetEmployeeIdRequest request) {
        GetEmployeeResponse response = new GetEmployeeResponse();

        if (request.getEmployeeId() == 0) {
            throw new IllegalArgumentException("Employee ID cannot be zero");
        }

        EmployeeInfo employee = new EmployeeInfo();
        BeanUtils.copyProperties(employeeService.selectEmployeeById(request.getEmployeeId()), employee);
        response.setEmployeeInfo(employee);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEmployeeRequest")
    @ResponsePayload
    public UpdateEmployeeResponse updateEmployee(@RequestPayload UpdateEmployeeRequest request) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(request.getEmployeeInfo(), employee);
        employeeService.updateEmployee(employee);
        ServiceStatus serverStatus = new ServiceStatus();
        serverStatus.setStatus("Success");
        serverStatus.setMessage("Employee updated successfully");
        UpdateEmployeeResponse response = new UpdateEmployeeResponse();
        response.setServiceStatus(serverStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmployeeRequest")
    @ResponsePayload
    public DeleteEmployeeResponse deleteEmployee(@RequestPayload DeleteEmployeeRequest request) {
        employeeService.deleteEmployee(request.getEmployeeId());
        ServiceStatus serverStatus = new ServiceStatus();
        serverStatus.setStatus("Success");
        serverStatus.setMessage("Employee deleted successfully");
        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
        response.setServiceStatus(serverStatus);
        return response;
    }
}

