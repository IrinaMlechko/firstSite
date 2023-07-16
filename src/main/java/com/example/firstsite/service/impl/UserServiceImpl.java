package com.example.firstsite.service.impl;

import com.example.firstsite.dao.impl.UserDaoImpl;
import com.example.firstsite.entity.Credentials;
import com.example.firstsite.entity.User;
import com.example.firstsite.exception.DaoException;
import com.example.firstsite.exception.ServiceException;
import com.example.firstsite.service.UserService;
import com.example.firstsite.util.PasswordEncryptor;
import com.example.firstsite.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {

        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean authenticate(String userName, String password) throws ServiceException {
        logger.info("Authenticate user " + userName);
        if (!Validator.validateUsername(userName)) {
            throw new ServiceException("Invalid username");
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match;
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            match = userDao.authenticate(userName, encryptedPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error encrypting password", e);
        }
        return match;
    }

    @Override
    public Optional<String> findName(String login) throws ServiceException {
        logger.info("Get name for the user with login " + login);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<String> firstName;
        try {
            firstName = userDao.findUserFirstNameByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return firstName;
    }

    @Override
    public boolean existsByLogin(String login) throws ServiceException {
        logger.info("Check if user " + login + " already exists.");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match;
        try {
            match = userDao.existsByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

    @Override
    public int createUser(User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.createUser(user);
        } catch (DaoException e) {
            throw new ServiceException("Error creating user", e);
        }
    }

    @Override
    public void createCredentials(Credentials credentials) throws ServiceException {
        if (!Validator.validateUsername(credentials.getLogin())) {
            throw new ServiceException("Invalid username.");
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(credentials.getPassword());
            credentials.setPassword(encryptedPassword);
            userDao.createCredentials(credentials);
        } catch (DaoException e) {
            throw new ServiceException("Error creating credentials", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error encrypting password", e);
        }
    }
}
