package com.server.ecomm.authorizationserver.service;
import com.server.ecomm.dto.UserDTO;
import com.server.ecomm.authorizationserver.entity.User;
import com.server.ecomm.authorizationserver.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public boolean isUserExisted(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public User addUser(UserDTO userDTO){
        if(isUserExisted(userDTO.getEmail())){
            throw new RuntimeException("User existed");
        }
        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.isIsadmin()){
            newUser.setRole("ADMIN");
        } else{
            newUser.setRole("USER");
        }
        userRepository.save(newUser);
        return newUser;
    }

    public User updateUser(UserDTO userDTO){
        User existed_user = findUserByEmail(userDTO.getEmail());
        if(existed_user == null){
            throw new RuntimeException("User not found!");
        }else{
            existed_user.setEmail(userDTO.getEmail());
            existed_user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            if (userDTO.isIsadmin()){
                existed_user.setRole("ADMIN");
            } else{
                existed_user.setRole("USER");
            }
            userRepository.save(existed_user);
            return existed_user;
        }
    }

    public boolean deleteUser(User user){
        User existed_user = findUserByEmail(user.getEmail());
        if(existed_user != null){
            userRepository.delete(existed_user);
            return true;
        }
        return false;
    }
}
