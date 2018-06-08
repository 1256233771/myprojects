package cins.art.numproduct.service.impl;

import cins.art.numproduct.entity.User;
import cins.art.numproduct.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
//    @Test
//    public void findUserByEmail() {
//        userService.addUser(new User("123","email","password","user"));
//        User user = userService.findUserByEmail("email");
//        log.info("user:{}",user);
//
//    }

    @Test
    public void addUser() {
    }
}