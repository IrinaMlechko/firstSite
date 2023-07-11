package com.example.firstsite.service.impl;

import com.example.firstsite.dao.impl.UserDaoImpl;
import com.example.firstsite.entity.Credentials;
import com.example.firstsite.entity.User;
import com.example.firstsite.exception.DaoException;
import com.example.firstsite.exception.ServiceException;
import com.example.firstsite.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
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
        //validate login, password, md5
        logger.info("Authenticate user " + userName);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match;
        try {
            String encryptedPassword = encryptPassword(password);
            match = userDao.authenticate(userName, encryptedPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

    private String encryptPassword(String password) throws ServiceException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
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
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            String encryptedPassword = encryptPassword(credentials.getPassword());
            credentials.setPassword(encryptedPassword);
            userDao.createCredentials(credentials);
        } catch (DaoException e) {
            throw new ServiceException("Error creating credentials", e);
        }
    }

}
