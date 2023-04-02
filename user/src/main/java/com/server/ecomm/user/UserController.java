package com.server.ecomm.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthServiceProxy authServiceProxy;

    @Autowired
    public UserController(UserService userService, AuthServiceProxy authServiceProxy) {
        this.userService = userService;
        this.authServiceProxy = authServiceProxy;
    }

    @GetMapping("/home")
    public String home(){
        return "<h1>Welcome to User service</h1>";
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        if (userService.updateUserAdmin(id, user) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            authServiceProxy.updateUser(user);
        } catch (Exception e){
            log.error(e.toString());
        }
        return new ResponseEntity<>((HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        if (userService.deleteUser(id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.addUser(user);

        if (createdUser == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            authServiceProxy.createUser(user);
        } catch (Exception e){
            log.error(e.toString());
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Need to fix. Get user id via current login user
    @PutMapping("/profile/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody User user){
        if (userService.updateUser(id, user) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
