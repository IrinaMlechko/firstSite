package com.example.firstsite.dao;

import com.example.firstsite.entity.User;
import com.example.firstsite.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao <Integer, User> {
    Optional<User> findEntityById(Integer id) throws DaoException;

    Optional<User> findEntityById(Long id) throws DaoException;

    List<User> findUserByName(String name) throws DaoException;
    boolean authentificate(String name, String password) throws DaoException;
}
