package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.dao.UserDao;
import camt.se331.shoppingcart.entity.*;
import camt.se331.shoppingcart.repository.UserRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
    private UserDao userDao;

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
        image = ImageUtil.resizeImage(image, 200);
        user.getImages().add(image);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User autoLogin(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User Login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String PassUserFromDB = user.getPassword();
            if (PassUserFromDB.equals(password)) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }

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
            if (img.getId().intValue() == id.intValue()) {
                user.getImages().remove(img);
            }
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public String Checkin(User user, Checkin checkin) {
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
                            userRepository.save(user);
                            return new String("Clock out");
                        } else if (type.equals("Clock out")) {
                            return new String("Failed");
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
        user.getCheckins().add(checkin);
        userRepository.save(user);
        return new String("Clock in");
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
        while (roleitr.hasNext()) {
            Role role = roleitr.next();
            if (role.getId().intValue() == roleid.intValue()) {
                user.getRoles().remove(role);
            }

        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User verifyEmail(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public String dailyCheck(Long id) {
        User user = userRepository.findOne(id);

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
        Set<Checkin> LatestCheckin = new HashSet<Checkin>();
        LatestCheckin = user.getCheckins();
        System.out.println(LatestCheckin);
        for (Checkin s : LatestCheckin) {
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
                            System.out.println("Clock out =======================");
                            return new String("Clock out");
                        } else if (type.equals("Clock out")) {
                            System.out.println("Failed =======================");
                            return new String("Failed");
                        }
                    }
                }
            }
        }
        System.out.println("Clock in =======================");
        return new String("Clock in");

    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

}
