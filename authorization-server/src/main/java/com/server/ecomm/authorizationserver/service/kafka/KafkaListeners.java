package com.server.ecomm.authorizationserver.service.kafka;

import com.server.ecomm.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListeners {

    @KafkaListener(topics = "test2", groupId = "group1")
    public void listen(UserDTO userDTO) {
//        UserDTO userDTO = record.value();
        log.info("Received Messasge, email: " + userDTO.getEmail());
        System.out.println("Received Messasge, email: " + userDTO);
    }
}
