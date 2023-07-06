package com.example.firstsite.service.impl;

import com.example.firstsite.dao.impl.UserDaoImpl;
import com.example.firstsite.exception.DaoException;
import com.example.firstsite.exception.ServiceException;
import com.example.firstsite.service.UserService;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {

        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean authenticate(String userName, String password) throws ServiceException {
        //validate login, password, md5
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try{ match = userDao.authentificate(userName, password);}
        catch(DaoException e){
            throw new ServiceException(e);
        }
        return match;
    }
}
