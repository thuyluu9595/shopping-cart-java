package com.example.authorizationserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthModel {
    private String email;
    private String password;
}
