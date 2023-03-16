package com.server.ecomm.apigateway.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String errorMsg;
}
