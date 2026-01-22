package com.crm.service;



import com.crm.DTOs.EmployeeCreateDto;
import com.crm.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmployeeService {
    public Employee createEmployee(EmployeeCreateDto employeeDetails);

    ResponseEntity<List<Employee>> getAllEmployees();

    void assignProject(long projectId, long employeeId, boolean isManager);
}