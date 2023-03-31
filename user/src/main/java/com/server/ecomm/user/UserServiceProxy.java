package com.server.ecomm.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "localhost:8085")
public interface UserServiceProxy {

    void createUser(@RequestBody User user);
    void updateUser(@RequestBody User user);

    void deleteUser(@RequestBody User user);
}
