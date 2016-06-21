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
    CompanyRoleRepository companyRoleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

        CompanyRole[] companyRoles = {
                new CompanyRole(1l, "Chairman"),
                new CompanyRole(2l, "President"),
                new CompanyRole(3l, "Senior Advisor"),
                new CompanyRole(4l, "Research and Development Manager"),
                new CompanyRole(5l, "Human Resource"),
                new CompanyRole(6l, "Manufacturing Manager"),
                new CompanyRole(7l, "Technical Manager"),
                new CompanyRole(8l, "Production Control Manager"),
                new CompanyRole(9l, "Assurance Manager"),
                new CompanyRole(10l, "Finance Manager"),
                new CompanyRole(10l, "Marketing Manager")
        };

        companyRoleRepository.save(Arrays.asList(companyRoles));


        Role adminRole = new Role("Admin");

        User admin = new User();
        admin.setId(1l);
        admin.setName("Narutchai Pipatwasukun");
        admin.setUsername("admin");
        admin.setEmail("se562115022@vr.camt.info");
        admin.setPassword("123456");
        admin.setCompanyrole("Human Resource");
        admin.getImages().add(ImageUtil.getImage("pic/apocalypse.png"));
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        admin.setRoles(roles);

        userRepository.save(admin);


        User[] standardUser = {
                new User(2l, "user@gmail.com", "HelloWorld CallOpop", "0875404521", "user", "123456", "Senior Advisor", ImageUtil.getImage("pic/angular.png")),
                new User(3l, "user2@gmail.com", "Mustrean Guanggang", "0826483155", "user1", "123456", "Assurance Manager", ImageUtil.getImage("pic/bootstrap.png")),
                new User(4l, "user2@gmail.com", "Justice League", "0826483155", "user2", "123456", "Research and Development Manager", ImageUtil.getImage("pic/protractor.png")),
                new User(5l, "user3@gmail.com", "Beast Warfax", "0945358754", "user3", "123456", "Technical Manager", ImageUtil.getImage("pic/browsersync.png")),
                new User(6l, "user4@gmail.com", "Tim Wrexham", "0904123345", "user4", "123456", "Assurance Manager", ImageUtil.getImage("pic/gulp.png")),
                new User(7l, "user5@gmail.com", "Muchearano Altony", "0834568582", "user5", "123456", "Manufacturing Manager", ImageUtil.getImage("pic/jasmine.png")),
                new User(8l, "user6@gmail.com", "Winterwolf Allstar", "0865664858", "user6", "123456", "Assurance Manager", ImageUtil.getImage("pic/node-sass.png")),
                new User(9l, "user7@gmail.com", "King Rexar", "0915486763", "user7", "123456", "Technical Manager", ImageUtil.getImage("pic/karma.png"))
        };

        userRepository.save(Arrays.asList(standardUser));

    }
}
