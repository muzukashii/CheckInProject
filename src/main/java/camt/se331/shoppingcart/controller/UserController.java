package camt.se331.shoppingcart.controller;
import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
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
    public ResponseEntity<User> Login(@RequestParam("username") String username, @RequestParam("password")String password){
        User user = userService.Login(username,password);
        if (user != null) {
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "userControl/{id}",method = RequestMethod.GET)
    public  User getUserbyId(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @RequestMapping(value = "autoLogin",method = RequestMethod.GET)
    public  User autoLogin(@RequestParam("username") String username){
        return userService.autoLogin(username);
    }

    @RequestMapping(value = "userControl/{id}",method = RequestMethod.PUT)
    public  User edit(@PathVariable("id") Long id,@RequestBody User user, BindingResult bindingResult){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "role",method = RequestMethod.PUT)
    public  User addRole(@RequestBody User user){
        return userService.addRoletoUser(user);
    }

    @RequestMapping(value = "role",method = RequestMethod.DELETE)
    @ResponseBody public  User deleteRole(@RequestParam("userid") Long userId,@RequestParam("roleid") Long roleId){
        User user = userService.getUser(userId);
        return userService.RemoveRole(user,roleId);
    }

    @RequestMapping(value = "/userimage/add", method = RequestMethod.POST)
    @ResponseBody public User addImage(HttpServletRequest request,
                         HttpServletResponse response, @RequestParam("UserId") Long UserId) {
        MultipartHttpServletRequest mRequest;
        User user = userService.getUser(UserId);
        try {
            mRequest = (MultipartHttpServletRequest) request;
            Iterator<String> itr = mRequest.getFileNames();
            while (itr.hasNext()) {
                MultipartFile multipartFile = mRequest.getFile(itr.next());
                Image image2 = new Image();
                image2.setFileName(multipartFile.getOriginalFilename());
                image2.setContentType(multipartFile.getContentType());
                image2.setContent(multipartFile.getBytes());
                image2.setCreated("");
                userService.addImage(user,image2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return user;
    }

    @RequestMapping(value = "/userimage/remove",method = RequestMethod.DELETE)
    @ResponseBody
    public  User removeImage(@RequestParam("userid") Long userId,@RequestParam("imageid") Long imageid){
        User user = userService.getUser(userId);
        //System.out.println("----------- " + productId + " --------" + imageid);
        return userService.removeImage(user,imageid);
    }



}
