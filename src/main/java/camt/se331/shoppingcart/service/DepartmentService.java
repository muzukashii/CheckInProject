package camt.se331.shoppingcart.service;
import camt.se331.shoppingcart.entity.Department;

import java.util.List;

/**
 * Created by Bitee on 6/14/2016.
 */
public interface DepartmentService {
    List<Department> getCompanyRoleList();
    Department addCompanyRole(Department department);
}
