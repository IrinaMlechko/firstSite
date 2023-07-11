package com.example.firstsite.dao;

import com.example.firstsite.entity.BaseEntity;
import com.example.firstsite.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends BaseEntity> {
    List<T> findAll() throws DaoException;

    boolean delete(K id) throws DaoException;

    boolean create(T t) throws DaoException;

    T update(T t) throws DaoException;

}
