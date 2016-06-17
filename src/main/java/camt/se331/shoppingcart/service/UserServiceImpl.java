package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.config.DatabaseInitializationBean;
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
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User addImage(User user, Image image) {
        image=ImageUtil.resizeImage(image,200);
        user.getImages().add(image);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
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
    public User removeImage(User user, Long id) {
        Iterator<Image> imgitr = user.getImages().iterator();
        while (imgitr.hasNext()) {
            Image img = imgitr.next();
            if( img.getId().intValue() == id.intValue() ) {
                user.getImages().remove(img);
            }
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User Checkin(User user, Checkin checkin) {
        user.getCheckins().add(checkin);
        return userRepository.save(user);
    }

    @Override
    public User addRoletoUser(User user) {
        Role adminRole = new Role("Admin");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User RemoveRole(User user, Long roleid) {
        Iterator<Role> roleitr = user.getRoles().iterator();
        while(roleitr.hasNext()){
            Role role = roleitr.next();
            if(role.getId().intValue() == roleid.intValue()){
                user.getRoles().remove(role);
            }

        }
        userRepository.save(user);
        return user;
    }


    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

}
