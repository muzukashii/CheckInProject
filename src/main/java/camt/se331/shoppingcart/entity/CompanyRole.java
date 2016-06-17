package camt.se331.shoppingcart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Bitee on 6/14/2016.
 */
@Entity
public class CompanyRole {
    @Id
    @GeneratedValue
    private Long id;
    private String rolename;

    public Long getId() {
        return id;
    }

    public String getCompanyrole() {
        return rolename;
    }

    public void setCompanyrole(String rolename) {
        this.rolename = rolename;
    }


    public void setId(Long id) {

        this.id = id;
    }

    public CompanyRole(Long id, String rolename){
        this.id=id;
        this.rolename=rolename;
    }

    public CompanyRole(String rolename){
        this.rolename=rolename;
    }

    public CompanyRole(){

    }
}
