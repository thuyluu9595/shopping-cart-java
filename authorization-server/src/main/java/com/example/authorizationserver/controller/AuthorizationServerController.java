package com.example.authorizationserver.controller;

import com.example.authorizationserver.entity.User;
import com.example.authorizationserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationServerController {

    private final UserService userService;

    public AuthorizationServerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(){
        return "<h1>Welcome to Authorization Service</h1>";
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasAuthority('USER')")
    public String user(){
        return "<h1>Welcome to User Endpoint</h1>";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin(){
        return "<h1>Welcome to Admin Endpoint</h1>";
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User existed_user = userService.findUserByEmail(user.getEmail());
        if(existed_user == null){
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
