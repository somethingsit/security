package com.example.security.fw.aop;

import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
