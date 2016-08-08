package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.config.DatabaseInitializationBean;
import camt.se331.shoppingcart.dao.UserDao;
import camt.se331.shoppingcart.entity.*;
import camt.se331.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public User addImage(User user, Image image) {
        return userDao.addImage(user,image);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public User autoLogin(String username) {
        return userDao.autoLogin(username);
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User Login(String username, String password) {
        return userDao.Login(username,password);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }


    @Override
    public User removeImage(User user, Long id) {
        return userDao.removeImage(user,id);
    }

    @Override
    public User Checkin(User user, Checkin checkin) {
        return userDao.Checkin(user,checkin);
    }

    @Override
    public User addRoletoUser(User user) {
        return userDao.addRoletoUser(user);
    }

    @Override
    public User RemoveRole(User user, Long roleid) {
        return userDao.RemoveRole(user,roleid);
    }


    @Override
    public User addUser(User user) {
        return userDao.addUser(user);
    }

}
