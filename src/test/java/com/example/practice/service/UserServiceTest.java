package com.example.practice.service;

import com.example.practice.entity.User;
import com.example.practice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void test(User user) {
//        assertEquals(4, 2+2);
        assertTrue(userService.saveNewUser(user));
        assertNotNull(userRepository.findByUsername(user.getUsername()));
    }


}
