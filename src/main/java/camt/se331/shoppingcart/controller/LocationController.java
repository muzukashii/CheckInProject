package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Bitee on 4/30/2016.
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class LocationController {
    @Autowired
    LocationService locationService;

    @RequestMapping(value = "mylocation", method = RequestMethod.GET)
    public String test(@RequestParam("UserLocate") String Location) {
        LocalDateTime time = LocalDateTime.now();
        locationService.addLocation(Location);
        System.out.print(time);
        System.out.println(Location);
        return Location;
    }

    @RequestMapping(value = "getLocation", method = RequestMethod.GET)
    public ArrayList<String> getLocation() {
        return locationService.getLocationList();
    }
}
