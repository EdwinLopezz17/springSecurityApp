package com.app.SpringSecurityApp.app.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String message;
    private Boolean success;
}
