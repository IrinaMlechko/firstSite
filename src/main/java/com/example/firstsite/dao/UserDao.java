package com.example.firstsite.dao;

import com.example.firstsite.entity.User;
import com.example.firstsite.exception.DaoException;

import java.util.List;

public interface UserDao extends BaseDao <Integer, User> {
    User findEntityById(Long id) throws DaoException;

    List<User> findUserByName(String name) throws DaoException;
    boolean authentificate(String name, String password) throws DaoException;
}
