package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * Created by Bitee on 6/5/2016.
 */
@CrossOrigin
@Controller
@RequestMapping("/userimage")
public class UserImageController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public User addImage(HttpServletRequest request,
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

    @RequestMapping(value = "/remove",method = RequestMethod.DELETE)
    @ResponseBody
    public  User edit(@RequestParam("userid") Long userId,@RequestParam("imageid") Long imageid){
        User user = userService.getUser(userId);
        //System.out.println("----------- " + productId + " --------" + imageid);
        return userService.removeImage(user,imageid);
    }
}
