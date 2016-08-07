package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.Checkin;
import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.User;

import java.util.List;
import java.util.StringJoiner;


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
     User Checkin(User user, Checkin checkin);
     User addRoletoUser(User user);
     User RemoveRole(User user, Long roleid);
     boolean ValidateEmail(String email);
}
