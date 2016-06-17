package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Checkin;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Bitee on 6/15/2016.
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class UserCheckinController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "checkin", method = RequestMethod.POST)
    public @ResponseBody
    User add(@RequestParam("UserId") Long UserId , @RequestBody Checkin checkin, BindingResult bindingResult ) {
        User user = userService.getUser(UserId);
        return userService.Checkin(user,checkin);
    }
}
