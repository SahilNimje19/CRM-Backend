package com.crm.controller;

import com.crm.DTOs.EmployeeCreateDto;

import com.crm.model.Employee;
import com.crm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee>
    createEmployee(@RequestBody EmployeeCreateDto dto){
        Employee employee = employeeService.createEmployee(dto);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @PutMapping("/assign/{projectId}/{employeeId}")
    public String assignProject(
            @PathVariable long projectId,
            @PathVariable long employeeId,
            @RequestParam boolean isManager
    ){
        employeeService.assignProject(projectId,employeeId,isManager);

        return "Project assigned successfully.";
    }
}