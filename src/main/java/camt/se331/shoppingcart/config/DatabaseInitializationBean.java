package camt.se331.shoppingcart.config;

import camt.se331.shoppingcart.dao.ShoppingCartDao;
import camt.se331.shoppingcart.entity.*;
import camt.se331.shoppingcart.repository.ProductRepository;
import camt.se331.shoppingcart.repository.ShoppingCartRepository;
import camt.se331.shoppingcart.repository.UserRepository;
import camt.se331.shoppingcart.service.ImageUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Dto on 2/11/2015.
 */
@Component
@Profile("db.init")
public class DatabaseInitializationBean implements InitializingBean {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public void afterPropertiesSet() throws Exception {
        Product[] initProduct = {
                new Product(1l, "Kindle", "the good book reader", 6900.00,6000.00, ImageUtil.getImage("pic/angular.png")),
                new Product(2l, "Surface Pro", "The unknow computer", 34000.00,30000.00, ImageUtil.getImage("pic/bootstrap.png")),
                new Product(3l, "Mac pro", " Mac book interim", 44000.00, 40000.00, ImageUtil.getImage("pic/browsersync.png")),
                new Product(4l, "Candle", "use for lightenup the world", 10.00, 5.00 , ImageUtil.getImage("pic/gulp.png")),
                new Product(5l, "Bin", "User for what ?", 200.00, 150.00, ImageUtil.getImage("pic/jasmine.png")),
                new Product(6l, "Telephone", "Call the others", 150.00, 100.00 , ImageUtil.getImage("pic/karma.png")),
                new Product(7l, "iPhone", "What is it?", 26000.00, 20000.00, ImageUtil.getImage("pic/node-sass.png")),
                new Product(8l, "Galaxy Note 4", "Who still use this ?", 24000.00, 20000.00, ImageUtil.getImage("pic/protractor.png")),
                new Product(9l, "AngularJS", "we hate it", 2000.00,1000.00, ImageUtil.getImage("pic/ui-bootstrap.png")),
                new Product(10l, "Mazda 3", "Very handsome guy use this", 300000.00,200.00, ImageUtil.getImage("pic/yeoman.png"))
        };
        productRepository.save(Arrays.asList(initProduct));

//
//        ShoppingCart shoppingCart = new ShoppingCart();
//        List<SelectedProduct> selectedProducts = new ArrayList<>();
//        SelectedProduct[] initSelectedProduct = {
//                new SelectedProduct(initProduct[2], 5),
//                new SelectedProduct(initProduct[4], 2),
//                new SelectedProduct(initProduct[1], 1),
//        };
//        selectedProducts.addAll(Arrays.asList(initSelectedProduct));
//        Calendar calendar = new GregorianCalendar(2015, 4, 7);
//        shoppingCart.setSelectedProducts(selectedProducts);
//        shoppingCart.setPurchaseDate(calendar.getTime());
//        shoppingCart.setId(2L);
//        shoppingCartRepository.save(shoppingCart);

        // add user
        Role adminRole = new Role("Admin");
        Role userRole = new Role("Retail");
        Role userRole2 = new Role("WholeSale");


        User admin = new User();
        admin.setName("admin");
        admin.setUsername("admin");
        admin.setEmail("admin@yahoo.com");
        admin.setPassword("123456");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        admin.setRoles(roles);

        User user = new User();
        user.setName("user");
        user.setUsername("user");
        user.setEmail("user@yahoo.com");
        user.setPassword("123456");
        Set<Role> roles2 = new HashSet<>();
        roles2.add(userRole);
        user.setRoles(roles2);

        User user2 = new User();
        user2.setName("user2");
        user2.setUsername("user2");
        user2.setEmail("user@yahoo.com");
        user2.setPassword("123456");
        Set<Role> roles3 = new HashSet<>();
        roles3.add(userRole2);
        user2.setRoles(roles3);


        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(user2);
    }
}
