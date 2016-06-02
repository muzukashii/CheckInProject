package camt.se331.shoppingcart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Bitee on 4/30/2016.
 */
@Entity
public class Location {
    @Id
    @GeneratedValue
    Long id;
    String LocationName;

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location(String locationName) {
        LocationName = locationName;
    }
}
