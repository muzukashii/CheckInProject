package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.Department;
import camt.se331.shoppingcart.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bitee on 6/14/2016.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getCompanyRoleList() {
        return departmentRepository.findAll();
    }

    @Override
    public Department addCompanyRole(Department department) {
        return departmentRepository.save(department);
    }
}
