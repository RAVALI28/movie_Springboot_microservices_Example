package com.springboot.microservices.DTO;

import lombok.Getter;

@Getter
public class Error {

    private final String reason;
    private final String message;

    public Error(String reason, String message){
        this.reason = reason;
        this.message = message;
    }
}
