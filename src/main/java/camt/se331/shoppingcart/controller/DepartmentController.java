package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Department;
import camt.se331.shoppingcart.repository.DepartmentRepository;
import camt.se331.shoppingcart.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Bitee on 6/14/2016.
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "department", method = RequestMethod.GET)
    public List<Department> getDepartmentList() {
        return departmentService.getCompanyRoleList();
    }

    @RequestMapping(value= "department", method= RequestMethod.POST)
    public @ResponseBody
    Department addDepartment(@RequestBody Department department , BindingResult bindingResult) {
        return departmentService.addCompanyRole(department);
    }

}
