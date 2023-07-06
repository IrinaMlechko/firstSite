package com.example.firstsite.dao;

import com.example.firstsite.entity.BaseEntity;
import com.example.firstsite.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<K, T extends BaseEntity> {
    List<T> findAll() throws DaoException;

    T findEntityById(K id) throws DaoException;

    boolean delete(K id) throws DaoException;

    boolean create(T t) throws DaoException;

    T update(T t) throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) { // log
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close(); // or connection return code to the pool
            }
        } catch (SQLException e) {
// log
        }
    }
}
