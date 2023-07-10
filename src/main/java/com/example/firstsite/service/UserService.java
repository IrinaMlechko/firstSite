package com.example.firstsite.service;

import com.example.firstsite.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    boolean authenticate(String userName, String password) throws ServiceException;

    Optional<String> findName(String login) throws ServiceException;

    boolean existsByLogin(String login) throws ServiceException;
}
