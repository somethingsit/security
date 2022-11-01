package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtsecurityApplication {

    public static void main(String[] args) {
		SpringApplication.run(JwtsecurityApplication.class, args);
	}
}
