package com.crm.controller;

import com.crm.model.Department;
import com.crm.model.User;
import com.crm.service.DeptService;
import com.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DeptService deptService;

    @PostMapping("/create")
    public ResponseEntity<String> createDepartment(@RequestBody Department department) {
        deptService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body("Department created successfully");
    }
}
