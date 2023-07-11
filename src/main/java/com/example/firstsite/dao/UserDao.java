package com.example.firstsite.dao;

import com.example.firstsite.entity.Credentials;
import com.example.firstsite.entity.User;
import com.example.firstsite.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao <Integer, Credentials> {

    Optional<String> findUserFirstNameByLogin(String login) throws DaoException;
    boolean authenticate(String name, String password) throws DaoException;

    boolean existsByLogin(String login) throws DaoException;

    int createUser(User user) throws DaoException;

    void createCredentials(Credentials credentials) throws DaoException;
}
