package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Checkin;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<JSONObject> checkin(@RequestParam("UserId") Long UserId , @RequestBody Checkin checkin, BindingResult bindingResult ) {
        User user = userService.getUser(UserId);
        String result = userService.Checkin(user,checkin);
        JSONObject obj = new JSONObject();
        if(result.equals("Clock in")){
            obj.put("result","Clock in Success!");
            return ResponseEntity.ok(obj);
        }else if (result.equals("Clock out")){
            obj.put("result","Clock out Success!");
            return ResponseEntity.ok(obj);
        }else if(result.equals("Failed")){
            obj.put("result","Failed to check in (Today, you're already Clock in & Clock out)");
            return ResponseEntity.ok(obj);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
