package com.server.ecomm.user.config.proxy;

import com.server.ecomm.user.DTOs.UserDTO;
import com.server.ecomm.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authorization-service", url = "localhost:8085")
public interface AuthServiceProxy {
    @PostMapping("/api/v1/validateToken/register")
    void createUser(@RequestBody User user);

    @PutMapping("/api/v1/validateToken/update")
    void updateUser(@RequestBody User user);

    @DeleteMapping("/api/v1/validateToken/delete")
    void deleteUser(@RequestBody User user);
}
