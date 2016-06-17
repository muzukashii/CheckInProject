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
     User findByUserName(String username);
     User findUserByEmail(String username);
     User login(String email, String password);
     User updateUser(User user);
     User ChangeRoleUserToAdmin(User user);
     User removeImage(User user, Long id);
     User Checkin(User user, Checkin checkin);
}
