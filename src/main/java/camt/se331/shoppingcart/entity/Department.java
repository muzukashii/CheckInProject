package camt.se331.shoppingcart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Bitee on 6/14/2016.
 */
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    private String rolename;

    public Long getId() {
        return id;
    }

    public String getDepartment() {
        return rolename;
    }

    public void setDepartment(String rolename) {
        this.rolename = rolename;
    }


    public void setId(Long id) {

        this.id = id;
    }

    public Department(Long id, String rolename){
        this.id=id;
        this.rolename=rolename;
    }

    public Department(String rolename){
        this.rolename=rolename;
    }

    public Department(){

    }
}
