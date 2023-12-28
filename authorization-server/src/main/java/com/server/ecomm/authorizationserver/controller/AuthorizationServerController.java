package com.server.ecomm.authorizationserver.controller;

import com.server.ecomm.authorizationserver.entity.User;
import com.server.ecomm.authorizationserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import com.server.ecomm.authorizationserver.model.ConnValidationResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/validateToken")
@Slf4j
public class AuthorizationServerController {

    private final UserService userService;

    public AuthorizationServerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(){
        return "<h1>Welcome to Authorization Service</h1>";
    }
//
    @GetMapping(value = "/user")
    @PreAuthorize("hasAuthority('USER')")
    public String user(){
        return "<h1>Welcome to User Endpoint</h1>";
    }
//
    @GetMapping(value = "/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin(){
        return "<h1>Welcome to Admin Endpoint</h1>";
    }

    @PostMapping("/add")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        if(userService.addUser(user)){
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if(userService.updateUser(user)){
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        if(userService.deleteUser(user)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("")
    public ResponseEntity<ConnValidationResponse> validateToken(HttpServletRequest request){

        String email = request.getAttribute("email").toString();
        String token = request.getAttribute("token").toString();
        log.info(email + " " + token);
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) request.getAttribute("authorities");
        ConnValidationResponse response = ConnValidationResponse.builder()
                .status("OK")
                .methodType(HttpMethod.GET.name())
                .email(email)
                .authorities(grantedAuthorities)
                .isAuthenticated(true)
                .token(token)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
