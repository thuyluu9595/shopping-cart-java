package com.server.ecomm.authorizationserver.service;
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

    public boolean addUser(User user){
        if(isUserExisted(user.getEmail())){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean updateUser(User user){
        User existed_user = findUserByEmail(user.getEmail());
        if(existed_user == null){
            return false;
        }else{
            existed_user.setEmail(user.getEmail());
            existed_user.setPassword(passwordEncoder.encode(user.getPassword()));
            existed_user.setRole(user.getRole());
            userRepository.save(existed_user);
            return true;
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
