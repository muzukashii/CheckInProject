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
    private String location;
    private String day;
    private int year;
    private int month;
    private int date;
    private String time;
    private String type;

    public Checkin(String location, String day, int year, int month, int date, String time, String type) {
        this.location = location;
        this.day = day;
        this.year = year;
        this.month = month;
        this.date = date;
        this.time = time;
        this.type = type;
    }
    public Checkin(Long id, String location, String type) {
        this.id = id;
        this.location = location;
        this.type = type;
    }

    public Checkin(Long id, String location) {
        this.id = id;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
