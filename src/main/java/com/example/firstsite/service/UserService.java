package com.example.firstsite.service;

import com.example.firstsite.exception.DaoException;
import com.example.firstsite.exception.ServiceException;

public interface UserService {
    boolean authenticate(String userName, String password) throws ServiceException;
}
