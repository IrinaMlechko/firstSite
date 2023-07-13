package com.example.firstsite.util;

public class Validator {
    public static boolean validateUsername(String username) {
        int minUsernameLength = 1;
        int maxUsernameLength = 15;
        int usernameLength = username.length();
        return usernameLength >= minUsernameLength && usernameLength <= maxUsernameLength;
    }
}
