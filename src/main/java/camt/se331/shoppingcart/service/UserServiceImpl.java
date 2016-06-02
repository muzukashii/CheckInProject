package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.config.DatabaseInitializationBean;
import camt.se331.shoppingcart.entity.Role;
import camt.se331.shoppingcart.entity.ShoppingCart;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.repository.ShoppingCartRepository;
import camt.se331.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dto on 4/19/2015.
 */
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String username) {
        return null;
    }

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User ChangeRoleUserToAdmin(User user) {
        Role userRole = new Role("Admin");
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        Role userRole = new Role("Retail");
        Set<Role> roles2 = new HashSet<>();
        roles2.add(userRole);
        user.setRoles(roles2);
        return userRepository.save(user);
    }




}
