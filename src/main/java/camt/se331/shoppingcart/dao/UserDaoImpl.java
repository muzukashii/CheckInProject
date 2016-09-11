package camt.se331.shoppingcart.dao;

import camt.se331.shoppingcart.entity.Checkin;
import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.Role;
import camt.se331.shoppingcart.entity.User;
import camt.se331.shoppingcart.repository.UserRepository;
import camt.se331.shoppingcart.service.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Bitee on 8/8/2016.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;
}
