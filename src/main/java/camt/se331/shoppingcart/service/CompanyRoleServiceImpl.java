package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.CompanyRole;
import camt.se331.shoppingcart.repository.CompanyRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bitee on 6/14/2016.
 */
@Service
public class CompanyRoleServiceImpl implements  CompanyRoleService {
    @Autowired
    CompanyRoleRepository companyRoleRepository;

    @Override
    public List<CompanyRole> getCompanyRoleList() {
        return companyRoleRepository.findAll();
    }

    @Override
    public CompanyRole addCompanyRole(CompanyRole companyRole) {
        return companyRoleRepository.save(companyRole);
    }
}
