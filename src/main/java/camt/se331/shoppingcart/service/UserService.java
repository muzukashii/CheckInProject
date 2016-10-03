package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.Checkin;
import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.User;

import java.util.List;


public interface UserService {
     User addUser(User user);
     List<User> findAll();
     User addImage (User user,Image image);
     User getUser(Long id);
     User autoLogin(String username);
     User findByUserName(String username);
     User Login(String username,String password);
     User updateUser(User user);
     User removeImage(User user, Long id);
     String Checkin(User user, Checkin checkin);
     User addRoletoUser(User user);
     User RemoveRole(User user, Long roleid);
     User verifyEmail(String username);
     String dailyCheck(Long id);
     List<User> Search(String input);
}
