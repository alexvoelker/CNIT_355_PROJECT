package com.aeondynamics.cnit_355_project;
//UPDATED 11/17/24
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class Security {

    /**
     * Hashes a password using SHA-256.
     * @param password the plain text password
     * @return the hashed password
     */

    //Not sure if this is how we want to do it, but figured it was an option for security
    //Can adjust later as needed

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}