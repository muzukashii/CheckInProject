package camt.se331.shoppingcart.dao;

import camt.se331.shoppingcart.entity.Checkin;
import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.User;

import java.util.List;

/**
 * Created by Bitee on 8/8/2016.
 */
public interface UserDao {
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
    User verifyEmail(String username);
}
