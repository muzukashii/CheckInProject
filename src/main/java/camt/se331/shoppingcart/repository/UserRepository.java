package camt.se331.shoppingcart.repository;


import camt.se331.shoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Dto on 4/19/2015.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM User u WHERE u.name like ?1 or u.department like ?2")
    List<User> findByNameOrDepartmentContainingIgnoreCase(String name,String department);
}
