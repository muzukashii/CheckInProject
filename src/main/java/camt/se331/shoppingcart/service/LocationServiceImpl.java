package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.dao.LocationDao;
import camt.se331.shoppingcart.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Bitee on 4/30/2016.
 */
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    LocationDao locationDao;
    ArrayList<String> LocationArray = new ArrayList<>();
    @Override
    public ArrayList<String> addLocation(String location) {
        LocationArray.add(location);
        return LocationArray;
    }

    @Override
    public ArrayList<String> getLocationList() {
        return LocationArray;
    }
}
