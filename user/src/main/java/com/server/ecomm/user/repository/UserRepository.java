package com.server.ecomm.user.repository;

import com.server.ecomm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);
    User findUserByEmail(String email);

}
