package com.server.ecomm.user.service;

import com.server.ecomm.dto.UserDTO;
import com.server.ecomm.user.config.proxy.AuthServiceProxy;
import com.server.ecomm.user.entity.User;
import com.server.ecomm.user.repository.UserRepository;
import com.server.ecomm.user.ultil.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthServiceProxy authServiceProxy;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserService(UserRepository userRepository, AuthServiceProxy authServiceProxy, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.authServiceProxy = authServiceProxy;
        this.kafkaTemplate = kafkaTemplate;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(UserDTO userDTO){
        if (userRepository.findUserByEmail(userDTO.getEmail()) != null){
            throw new RuntimeException("Email has used!");
        }

        for (String adminEmail : Constants.adminEmails) {
            if (userDTO.getEmail().equals(adminEmail)){
                userDTO.setIsadmin(true);
                break;
            }
        }

        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.isIsadmin());

//        try {
//            authServiceProxy.createUser(user);
//        }
//        catch (Exception e) {
//            log.error(e.toString());
//            throw new RuntimeException("Cannot create user in Auth service");
//        }

        kafkaTemplate.send("test2", userDTO);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user){
        User existUser = userRepository.findById(id).orElse(null);
        if (existUser == null){
            return null;
        }

        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
//        existUser.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public boolean deleteUser(Long id){
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return false;
        }
        return true;
    }
}
