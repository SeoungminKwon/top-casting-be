package com.ll.topcastingbe.global.security;

public class SecurityConstants {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final int ACCESS_EXPIRATION_TIME = 5 * 60 * 60 * 1000; //5시간
//    public static final int ACCESS_EXPIRATION_TIME = 10 * 1000;
    public static final int REFRESH_EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 10; // 10일
//    public static final int REFRESH_EXPIRATION_TIME = 6 * 1000;

}
