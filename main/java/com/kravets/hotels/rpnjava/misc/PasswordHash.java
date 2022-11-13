package com.kravets.hotels.rpnjava.misc;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {

    public static String createHash(String login, String password, String salt) throws NoSuchAlgorithmException {
        String nonHashedPassword = login + '/' + password + '/' + salt;
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = messageDigest.digest(nonHashedPassword.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(hashedPassword);
    }
}
