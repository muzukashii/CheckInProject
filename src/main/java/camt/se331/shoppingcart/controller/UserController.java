package camt.se331.shoppingcart.controller;
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

    @RequestMapping(value = "userControl",method = RequestMethod.POST)
    public @ResponseBody
    User add(@RequestBody User user, BindingResult bindingResult){
        return userService.addUser(user);
    }

    @RequestMapping(value = "userControl",method = RequestMethod.GET)
    public List<User> list(){
        return userService.findAll();
    }

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public  User getUserbyUsername(@RequestParam("username")String username){
        return userService.findByUserName(username);
    }

    @RequestMapping(value = "userControl/{id}",method = RequestMethod.GET)
    public  User getUserbyId(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @RequestMapping(value = "userControl/{id}",method = RequestMethod.PUT)
    public  User edit(@PathVariable("id") Long id,@RequestBody User user, BindingResult bindingResult){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "role/{id}",method = RequestMethod.PUT)
    public  User addrole(@PathVariable("id") Long userId,@RequestBody User user){
        return userService.addRoletoUser(user);
    }

    @RequestMapping(value = "role",method = RequestMethod.DELETE)
    @ResponseBody
    public  User delete(@RequestParam("userid") Long userId,@RequestParam("roleid") Long roleid){
        User user = userService.getUser(userId);
        return userService.RemoveRole(user,roleid);
    }

//    //not use yet
//    @RequestMapping(value = "changera/{id}",method = RequestMethod.PUT)
//    public @ResponseBody User update(@RequestBody User user, BindingResult bindingResult){
//        return userService.ChangeRoleUserToAdmin(user);
//    }


}
