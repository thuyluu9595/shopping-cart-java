package com.server.ecomm.authorizationserver.controller;

import com.server.ecomm.authorizationserver.entity.User;
import com.server.ecomm.authorizationserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import com.server.ecomm.authorizationserver.model.ConnValidationResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/validateToken")
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

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User existed_user = userService.findUserByEmail(user.getEmail());
        if(existed_user == null){
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    @GetMapping("")
    public ResponseEntity<ConnValidationResponse> validateToken(HttpServletRequest request){

        String email = request.getAttribute("email").toString();
        String token = request.getAttribute("token").toString();
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
