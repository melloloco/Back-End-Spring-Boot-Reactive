package com.example.demo.application.userapplication;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class UserDTOOut {
    private String type;
    private String token;
    private String expireSeconds = "3600";
    private String refreshToken;
}