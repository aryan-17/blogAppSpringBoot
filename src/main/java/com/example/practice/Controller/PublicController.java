package com.example.practice.Controller;

import com.example.practice.entity.User;
import com.example.practice.service.UserService;
import com.example.practice.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }
}
