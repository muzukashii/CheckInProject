package camt.se331;

import camt.se331.shoppingcart.dao.UserDao;
import camt.se331.shoppingcart.dao.UserDaoImpl;
import camt.se331.shoppingcart.entity.Checkin;
import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import camt.se331.shoppingcart.service.ImageUtil;
import camt.se331.shoppingcart.service.UserServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Lab11Application.class)
@WebAppConfiguration
public class Lab11ApplicationTests {

    @Test
    public void testLogin() {
        UserServiceImpl userService = mock(UserServiceImpl.class);
        User user = new User("user@gmail.com", "123456");
        {
            User returnMock = new User(2l, "HelloWorld CallOpop", "0875404521", "user@gmail.com", "123456", "Senior Advisor");
            when(userService.Login(user.getUsername(), user.getPassword())).thenReturn(returnMock);
        }
        User resultSuccess = userService.Login(user.getUsername(), user.getPassword());
        assertEquals(user.getUsername(), resultSuccess.getUsername());
        assertNotNull(resultSuccess);
        System.out.println(resultSuccess);

        System.out.println("=================================================");
        System.out.println("In case of user input wrong password...");
        User user1 = new User("user@gmail.com", "123456789");
        when(userService.Login(user1.getUsername(), user1.getPassword())).thenReturn(null);
        User resultFailed1 = userService.Login(user1.getUsername(), user1.getPassword());
        assertNull(resultFailed1);
        System.out.println(resultFailed1);

        System.out.println("=================================================");
        System.out.println("In case of user input wrong email...");
        User user2 = new User("user5678@gmail.com", "123456");
        when(userService.Login(user2.getUsername(), user2.getPassword())).thenReturn(null);
        User resultFailed2 = userService.Login(user2.getUsername(), user2.getPassword());
        assertNull(resultFailed2);
        System.out.println(resultFailed2);


    }


    @Test
    public void testAddImage() throws IOException {
        UserServiceImpl userService = mock(UserServiceImpl.class);
        User user = new User(10l, "King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager");
        Image image = ImageUtil.getImage("pic/angular.png");
        {
            User returnMock = new User(10l, "King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager", ImageUtil.getImage("pic/angular.png"));
            when(userService.addImage(user, image)).thenReturn(returnMock);
        }
        User result = userService.addImage(user, image);
        System.out.println("First,Size of Array image is...");
        System.out.println(user.getImages().size());
        System.out.println(image.getFileName());
        assertEquals(user.getImages().size(), 0);
        System.out.println("------------------------");
        System.out.println("Last,Size of Array image is...");
        System.out.println(result.getImages().size());
//        System.out.println(result.getImages().);
        assertEquals(result.getImages().size(), 1);
        assertNotNull(result.getImages());
    }

    @Test
    public void testRemoveImage() throws IOException {
        UserServiceImpl userService = mock(UserServiceImpl.class);
        User user = new User(10l, "King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager", ImageUtil.getImage("pic/angular.png"));
        long id = 0l;
        {
            User returnMock = new User(10l, "King Rexar", "0915486763", "user25@gmail.com", "123456", "Technical Manager");
            when(userService.removeImage(user, id)).thenReturn(returnMock);
        }
        User result = userService.removeImage(user, id);
        System.out.println("First,Size of Array image is...");
        System.out.println(user.getImages().size());
        assertEquals(user.getImages().size(), 1);
        System.out.println("------------------------");
        System.out.println("Last,Size of Array image is...");
        System.out.println(result.getImages().size());
        assertEquals(result.getImages().size(), 0);
    }

    @Test
    public void testCheckIn() throws IOException, ParseException {
        UserServiceImpl userService = mock(UserServiceImpl.class);
        DateTimeFormatter dayFormat = DateTimeFormat
                .forPattern("E");
        DateTimeFormatter yearFormat = DateTimeFormat
                .forPattern("Y");
        DateTimeFormatter monthFormat = DateTimeFormat
                .forPattern("M");
        DateTimeFormatter dateFormat = DateTimeFormat
                .forPattern("d");
        DateTimeFormatter timeFormat = DateTimeFormat
                .forPattern("h:m a");
        LocalDate localDate = new LocalDate();
        LocalDateTime localDateTime = new LocalDateTime();
        String day = localDate.toString(dayFormat);
        String time = timeFormat.print(localDateTime);

        User user = new User(2l, "HelloWorld CallOpop", "0875404521", "user@gmail.com", "123456", "Senior Advisor");
        Checkin checkin = new Checkin("CAMT");

        //Get Check in history
        Set<Checkin> LatestCheckin = new HashSet<Checkin>();
        LatestCheckin = user.getCheckins();
        System.out.println("==========================");
        System.out.println(LatestCheckin);
        for (Checkin s : LatestCheckin) {
            System.out.println(s.getDate());
            int year = s.getYear();
            int currentYear = Integer.parseInt(yearFormat.print(localDateTime));
            if (year == currentYear) {
                int month = s.getMonth();
                int currentMonth = Integer.parseInt(monthFormat.print(localDateTime));
                if (month == currentMonth) {
                    int date = s.getDate();
                    int currentDate = Integer.parseInt(dateFormat.print(localDateTime));
                    if (date == currentDate) {
                        String type = s.getType();
                        if (type.equals("Clock in")) {
                            checkin.setDay(day);
                            checkin.setYear(Integer.parseInt(yearFormat.print(localDateTime)));
                            checkin.setMonth(Integer.parseInt(monthFormat.print(localDateTime)));
                            checkin.setDate(Integer.parseInt(dateFormat.print(localDateTime)));
                            checkin.setTime(time);
                            checkin.setType("Clock out");
                            user.getCheckins().add(checkin);
                            when(userService.Checkin(user,checkin)).thenReturn("Clock Out");
                            String resultClockOut = userService.Checkin(user,checkin);
                            System.out.println(resultClockOut);
                            assertEquals("Clock out",resultClockOut);
                        } else if (type.equals("Clock out")) {
                            when(userService.Checkin(user,checkin)).thenReturn("Failed");
                            String resultFailed = userService.Checkin(user,checkin);
                            System.out.println(resultFailed);
                            assertEquals("Failed",resultFailed);
                        }
                    }
                }
            }
        }
        checkin.setDay(day);
        checkin.setYear(Integer.parseInt(yearFormat.print(localDateTime)));
        checkin.setMonth(Integer.parseInt(monthFormat.print(localDateTime)));
        checkin.setDate(Integer.parseInt(dateFormat.print(localDateTime)));
        checkin.setTime(time);
        checkin.setType("Clock in");
        when(userService.Checkin(user,checkin)).thenReturn("Clock in");
        String resultClockIn = userService.Checkin(user,checkin);
        System.out.println(resultClockIn);
        assertEquals("Clock in",resultClockIn);
    }

}
