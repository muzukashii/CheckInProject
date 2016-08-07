package camt.se331.shoppingcart.entity;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dto on 4/19/2015.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String name;
    private String tel;
    private String username;
    private String password;
    private String companyrole;
    private Date dob;
    @ManyToMany(fetch= FetchType.EAGER)
    // Cascade and CascadeType must be the org.hibernate.annotation
    @Cascade(CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    Set<Image> images = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    Set<Checkin> checkins = new HashSet<>();

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Checkin> getCheckins() {
        return checkins;
    }

    public void setCheckins(Set<Checkin> checkins) {
        this.checkins = checkins;
    }

    public User(Long id, String name, String tel, String username, String password, String companyrole, Checkin checkin, Date dob, Image image) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.checkins.add(checkin);
        this.images.add(image) ;
        this.companyrole = companyrole;
    }
    public User(Long id, String name, String tel, String username, String password, String companyrole, Date dob, Image image) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.images.add(image) ;
        this.companyrole = companyrole;
    }
    public User(Long id, String name, String tel, String username, String password, String companyrole, Checkin checkin, Image image) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.images.add(image) ;
        this.checkins.add(checkin);
        this.companyrole = companyrole;
    }
    public User(Long id, String name, String tel, String username, String password, String companyrole, Image image) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.images.add(image) ;
        this.companyrole = companyrole;
    }

    public User(Long id, String name, String tel, String username, String password, String companyrole, Checkin checkin, Date dob) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.checkins.add(checkin);
        this.companyrole = companyrole;
    }

    public User(Long id, String name, String tel, String username, String password, String companyrole, Date dob) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.companyrole = companyrole;
    }


    public User(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (dob != null ? !dob.equals(user.dob) : user.dob != null) return false;
        return !(companyrole != null ? !companyrole.equals(user.companyrole) : user.companyrole != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (companyrole != null ? companyrole.hashCode() : 0);
        return result;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getCompanyrole() {
        return companyrole;
    }

    public void setCompanyrole(String companyrole) {
        this.companyrole = companyrole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", roles=" + roles +
                ", companyrole=" + companyrole +
                ", checkin=" + checkins +
                '}';
    }

}
