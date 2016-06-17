package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.CompanyRole;
import camt.se331.shoppingcart.service.CompanyRoleService;
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
public class CompanyRoleController {
    @Autowired
    CompanyRoleService companyRoleService;

    @RequestMapping(value = "companyrole", method = RequestMethod.GET)
    public List<CompanyRole> getCompanyRoleList() {
        return companyRoleService.getCompanyRoleList();
    }

    @RequestMapping(value= "companyrole", method= RequestMethod.POST)
    public @ResponseBody
    CompanyRole addCompanyRole(@RequestBody CompanyRole companyRole , BindingResult bindingResult) {
        return companyRoleService.addCompanyRole(companyRole);
    }

}
