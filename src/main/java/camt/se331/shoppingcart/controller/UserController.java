package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Product;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Bitee on 5/20/2016.
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "regis",method = RequestMethod.POST)
    public @ResponseBody
    User add(@RequestBody User user, BindingResult bindingResult){
        return userService.addUser(user);
    }

    @RequestMapping(value = "regis",method = RequestMethod.GET)
    public List<User> list(){
        return userService.findAll();
    }

    @RequestMapping(value = "regis/{id}",method = RequestMethod.PUT)
    public  User edit(@PathVariable("id") Long id,@RequestBody User user, BindingResult bindingResult){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "changera/{id}",method = RequestMethod.PUT)
    public @ResponseBody User update(@RequestBody User user, BindingResult bindingResult){
        return userService.ChangeRoleUserToAdmin(user);
    }


}
