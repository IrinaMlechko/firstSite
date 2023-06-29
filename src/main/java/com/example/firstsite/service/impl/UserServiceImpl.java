package com.example.firstsite.service.impl;

import com.example.firstsite.service.UserService;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String userName, String password){
        return userName.equals(password);
    }
}
