package com.server.ecomm.authorizationserver.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String password;
    private String email;
    private boolean isAdmin;
}
