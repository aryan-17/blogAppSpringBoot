package com.example.practice.service;

import com.example.practice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ActiveProfiles("prod")
public class CustomUserDetailsImplTest {

    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn((com.example.practice.entity.User) User.builder().username("ram").password("sbnsd").roles(String.valueOf(new ArrayList<>())).build());
        UserDetails user = customUserDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}