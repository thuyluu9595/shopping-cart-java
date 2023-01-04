package com.example.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(){
        return "<h1>Welcome to User service</h1>";
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUserAdmin(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user){
        return userService.addUser(user);
    }

    // Need to fix. Get user id via current login user
    @PutMapping("/profile/{id}")
    public User updateProfile(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

}
