package com.billa.springdatajpa.security;

public class SecurityConfig {
    public static final long expirationTime = 432_000_000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "jwt-token";
    public static final String TOKEN_CANT_BE_VERIFIED = "token cant be verified";
    public static final String GET_ARRAYS_LLC = "GATE FITMENT";
    public static final String GET_ARRAYS_LLC_ADMIN = "GATE FITMENT ADMIN";
    public static final String AUTHORITIES = "Authorities";
    public static final String OPTIONS = "Options";
    public static final String[] PUBLIC_URLS = {"/user/login","/user/register","/user/resetPassword/**","/user/image/**"};
    public static final String FORBIDDEN_MESSAGE = "you need proper creds to login";
    public static final String ACCESS_DENIED = "Access is denied ";
}
