package com.crm.service.Impl;

import com.crm.DTOs.EmployeeCreateDto;
import com.crm.model.Employee;
import com.crm.model.Project;
import com.crm.model.User;
import com.crm.repository.EmployeeRepository;
import com.crm.repository.ProjectRepository;
import com.crm.repository.UserRepository;
import com.crm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Employee createEmployee(EmployeeCreateDto employeeDetails) {
        if(employeeRepository.existsByEmail(employeeDetails.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUserName(employeeDetails.getUsername());
        user.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
        user.setRole("EMPLOYEE");
        userRepository.save(user);

        Employee employee = new Employee();
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setStatus(employeeDetails.getStatus());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setSalary(employeeDetails.getSalary());
        employee.setJoiningDate(LocalDate.parse(employeeDetails.getJoiningDate()));
        employee.setUser(user);

        return employeeRepository.save(employee);
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity(employeeRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public void assignProject(long projectId, long employeeId, boolean isManager) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new RuntimeException("Project not found."));
        Employee employee= employeeRepository.findById(employeeId).orElseThrow(()->
                new RuntimeException("Employee not found."));
        employee.getProjects().add(project);

        if(isManager){
            project.setManager(employee);
        }
        employeeRepository.save(employee);

    }
}