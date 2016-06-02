package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.Location;

import java.util.ArrayList;

/**
 * Created by Bitee on 4/30/2016.
 */
public interface LocationService {
    ArrayList<String> addLocation(String location);
    ArrayList<String> getLocationList();
}
