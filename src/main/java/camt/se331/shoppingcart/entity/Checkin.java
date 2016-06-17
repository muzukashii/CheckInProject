package camt.se331.shoppingcart.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import java.util.Date;

/**
 * Created by Bitee on 4/30/2016.
 */
@Entity
public class Checkin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private String location;
    private String type;

    public Checkin(Long id, Date time, String location, String type) {
        this.id = id;
        this.location = location;
        this.type = type;
        this.time = time;
    }

    public Checkin(Long id, Date time, String location) {
        this.id = id;
        this.time = time;
        this.location = location;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Checkin() {
    }

    public Checkin(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
