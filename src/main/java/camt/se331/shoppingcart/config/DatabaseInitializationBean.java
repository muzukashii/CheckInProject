package camt.se331.shoppingcart.config;

import camt.se331.shoppingcart.entity.*;
import camt.se331.shoppingcart.repository.*;
import camt.se331.shoppingcart.service.ImageUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.util.*;

/**
 * Created by Dto on 2/11/2015.
 */
@Component
@Profile("db.init")
public class DatabaseInitializationBean implements InitializingBean {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

        Department[] departments = {
                new Department(1l, "Chairman"),
                new Department(2l, "President"),
                new Department(3l, "Senior Advisor"),
                new Department(4l, "Research and Development Manager"),
                new Department(5l, "Human Resource"),
                new Department(6l, "Manufacturing Manager"),
                new Department(7l, "Technical Manager"),
                new Department(8l, "Production Control Manager"),
                new Department(9l, "Assurance Manager"),
                new Department(10l, "Finance Manager"),
                new Department(10l, "Marketing Manager")
        };

        departmentRepository.save(Arrays.asList(departments));


        Role adminRole = new Role("Admin");

        User admin = new User();
        admin.setId(1l);
        admin.setName("Narutchai Pipatwasukun");
        admin.setUsername("admin@gmail.com");
        admin.setPassword("123456");
        admin.setTel("0946553200");
        admin.setDepartment("Human Resource");
        admin.getImages().add(ImageUtil.getImage("pic/apocalypse.png"));
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        admin.setRoles(roles);

        userRepository.save(admin);


        User[] standardUser = {
                new User(2l, "HelloWorld CallOpop", "0875404521", "user@gmail.com", "123456", "Senior Advisor", ImageUtil.getImage("pic/angular.png")),
                new User(3l, "Mustrean Guanggang", "0826483155", "user2@gmail.com", "123456", "Assurance Manager", ImageUtil.getImage("pic/bootstrap.png")),
                new User(4l, "Justice League", "0826483155", "user3@gmail.com", "123456", "Research and Development Manager", ImageUtil.getImage("pic/protractor.png")),
                new User(5l, "Beast Warfax", "0945358754", "user4@gmail.com", "123456", "Technical Manager", ImageUtil.getImage("pic/browsersync.png")),
                new User(6l, "Tim Wrexham", "0904123345", "user5@gmail.com", "123456", "Assurance Manager", ImageUtil.getImage("pic/gulp.png")),
                new User(7l, "Muchearano Altony", "0834568582", "user6@gmail.com", "123456", "Manufacturing Manager", ImageUtil.getImage("pic/jasmine.png")),
                new User(8l, "Winterwolf Allstar", "0865664858", "user7@gmail.com", "123456", "Assurance Manager", ImageUtil.getImage("pic/node-sass.png")),
                new User(9l, "King Rexar", "0915486763", "user8@gmail.com", "123456", "Technical Manager", ImageUtil.getImage("pic/karma.png"))
        };

        userRepository.save(Arrays.asList(standardUser));

    }
}
