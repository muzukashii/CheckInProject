package camt.se331.shoppingcart.repository;


import camt.se331.shoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Dto on 4/19/2015.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
}
