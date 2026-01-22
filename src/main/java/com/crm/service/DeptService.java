package com.crm.service;

import com.crm.model.Department;
import com.crm.repository.DeptRepository;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
    private DeptRepository deptRepository;
    public Department createDepartment(Department department) {
        return deptRepository.save(department);
    }

    public Department addDepartment(Department d) {
        return deptRepository.save(d);
    }
}
