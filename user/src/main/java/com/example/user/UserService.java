package com.example.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user){
        User existUser = userRepository.findById(id).orElse(null);
        if (existUser == null){
            return null;
        }

        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        existUser.setPassword(user.getPassword());
        return userRepository.save(existUser);
    }

    public User updateUserAdmin(Long id, User user){
        User savedUser = userRepository.findById(id).orElse(null);
        if (savedUser == null){
            return null;
        }
        savedUser.setAdmin(user.isAdmin());
        savedUser.setName(user.getName());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(user.getPassword());
        return userRepository.save(savedUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
