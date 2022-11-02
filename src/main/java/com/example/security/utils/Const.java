package com.example.security.utils;

public class Const{
    public static class SECURE {
        public static final String SECRET = "SECRET_KEY";
        public static final long EXPIRATION_TIME = 900_000; // 15 mins
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
        public static final String SIGN_IN_URL = "/api/v01/login";
    }
}
