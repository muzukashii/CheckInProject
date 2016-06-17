package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.CompanyRole;

import java.util.List;

/**
 * Created by Bitee on 6/14/2016.
 */
public interface CompanyRoleService {
    List<CompanyRole> getCompanyRoleList();
    CompanyRole addCompanyRole(CompanyRole companyRole);
}
