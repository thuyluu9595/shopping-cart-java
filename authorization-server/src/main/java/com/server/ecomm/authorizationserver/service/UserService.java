package com.server.ecomm.authorizationserver.service;
import com.server.ecomm.authorizationserver.entity.User;
import com.server.ecomm.authorizationserver.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public void saveUser(User user){
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }
}
