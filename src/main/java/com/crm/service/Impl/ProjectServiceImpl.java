package com.crm.service.Impl;


import com.crm.model.Employee;
import com.crm.model.Project;
import com.crm.repository.EmployeeRepository;
import com.crm.repository.ProjectRepository;
import com.crm.repository.UserRepository;
import com.crm.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProjectRepository projectRepository;

    public Project createproject(Project project) {
        return projectRepository.save(project);
    }
    public void assignProject(long projectId, long employeeId, boolean isManager) {
        Project project = projectRepository.findById(projectId).orElseThrow( () -> new RuntimeException("project not found"));

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.getProjects().add(project);
        if(isManager) {
            project.setManager(employee);
        }
        employeeRepository.save(employee);
    }
}
