package com.example.firstsite.dao.impl;

import com.example.firstsite.dao.BaseDao;
import com.example.firstsite.dao.UserDao;
import com.example.firstsite.entity.User;
import com.example.firstsite.exception.DaoException;
import com.example.firstsite.pool.ConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao, BaseDao<Integer, User> {

    private static final String SELECT_PASSWORD = "SELECT password FROM web.credentials WHERE login = ?";
    private static final String SELECT_FIRST_NAME = "SELECT first_name FROM web.users us JOIN web.credentials cred ON us.id = cred.user_id WHERE login = ?";
    private static final String SELECT_LOGIN = "SELECT login FROM web.credentials WHERE login = ?";
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findEntityById(Integer id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean create(User user) throws DaoException {
        return false;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public boolean authentificate(String login, String password) throws DaoException {
        boolean match = false;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var statement = connection.prepareStatement(SELECT_PASSWORD)) {
            statement.setString(1, login);
            try (var resultSet = statement.executeQuery()) {
                String passFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(1);
                    match = password.equals(passFromDb);
                }
            }
        }  catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
    @Override
    public Optional<String> findUserFirstNameByLogin(String login) throws DaoException {
        Optional<String>  firstName = Optional.empty();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var statement = connection.prepareStatement(SELECT_FIRST_NAME)) {
            statement.setString(1, login);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    firstName = Optional.of(resultSet.getString(1));
                }
            }
        }  catch (SQLException e) {
            throw new DaoException(e);
        }
        return firstName;
    }
    @Override
    public boolean existsByLogin(String login) throws DaoException {
        boolean match = false;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var statement = connection.prepareStatement(SELECT_LOGIN)) {
            statement.setString(1, login);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    match = true;
                }
            }
        }  catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
}
