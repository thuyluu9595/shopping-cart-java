package com.server.ecomm.authorizationserver.security.services;

import com.server.ecomm.authorizationserver.entity.User;
import com.server.ecomm.authorizationserver.repository.UserRepository;
import com.server.ecomm.authorizationserver.dto.UserDetailsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserDetailsConverter::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found " + email));

    }
}
