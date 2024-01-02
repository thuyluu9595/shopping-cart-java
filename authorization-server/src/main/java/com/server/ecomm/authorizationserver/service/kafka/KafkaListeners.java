package com.server.ecomm.authorizationserver.service.kafka;

import com.server.ecomm.authorizationserver.service.UserService;
import com.server.ecomm.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListeners {
    private final UserService userService;

    public KafkaListeners(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "test2", groupId = "group1")
    public void listen(UserDTO userDTO) {
        userService.addUser(userDTO);
    }
}
