package camt.se331;

import camt.se331.shoppingcart.dao.UserDao;
import camt.se331.shoppingcart.dao.UserDaoImpl;
import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.User;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import camt.se331.shoppingcart.service.ImageUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Lab11Application.class)
@WebAppConfiguration
public class Lab11ApplicationTests {

	@Test
	public void testLogin() {
        UserDaoImpl userDao = mock(UserDaoImpl.class);
        User user = new User("user@gmail.com","123456");
        when(userDao.Login("user@gmail.com","123456")).thenReturn(null);
        {
            User returnMock = new User(2l, "HelloWorld CallOpop", "0875404521", "user@gmail.com", "123456", "Senior Advisor");
            when(userDao.Login(user.getUsername(),user.getPassword())).thenReturn(returnMock);
        }
        User result = userDao.Login(user.getUsername(),user.getPassword());
        assertEquals(user.getUsername(),result.getUsername());
        assertNotNull(result);
        System.out.print(result);

    }

    @Test
    public void testAddImage() throws IOException {
        UserDaoImpl userDao = mock(UserDaoImpl.class);
        User user = new User (10l,"King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager");
        Image image = ImageUtil.getImage("pic/angular.png");
        {
            User returnMock = new User(10l,"King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager",ImageUtil.getImage("pic/angular.png"));
            when(userDao.addImage(user,image)).thenReturn(returnMock);
        }
        User result = userDao.addImage(user,image);
        System.out.println("First,Size of Array image is...");
        System.out.println(user.getImages().size());
        System.out.println(image.getFileName());
        assertEquals(user.getImages().size(),0);
        System.out.println("------------------------");
        System.out.println("Last,Size of Array image is...");
        System.out.println(result.getImages().size());
//        System.out.println(result.getImages().);
        assertEquals(result.getImages().size(),1);
        assertNotNull(result.getImages());
    }

    @Test
    public void testRemoveImage() throws IOException {
        UserDaoImpl userDao = mock(UserDaoImpl.class);
        User user = new User(10l,"King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager",ImageUtil.getImage("pic/angular.png"));
        long id = 0l;
        {
            User returnMock = new User(10l,"King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager");
            when(userDao.removeImage(user,id)).thenReturn(returnMock);
        }
        User result = userDao.removeImage(user,id);
        System.out.println("First,Size of Array image is...");
        System.out.println(user.getImages().size());
        assertEquals(user.getImages().size(),1);
        System.out.println("------------------------");
        System.out.println("Last,Size of Array image is...");
        System.out.println(result.getImages().size());
        assertEquals(result.getImages().size(),0);
    }

}
