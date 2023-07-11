package com.example.firstsite.pool;

import com.example.firstsite.exception.ServiceException;
import com.example.firstsite.util.PropertiesStreamReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    public static final int CAPACITY = 3;
    public static final String PROPERTIES_FILE_NAME = "database.properties";
    private static final Properties properties = new Properties();
    private static ConnectionPool instance;
    private static String databaseUrl;
    private static Lock lock = new ReentrantLock(true);
    private static PropertiesStreamReader propertiesStreamReader = new PropertiesStreamReader();
    private BlockingQueue<ProxyConnection> connections = new LinkedBlockingQueue<>(CAPACITY);
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
        try {
            properties.load(new FileReader(propertiesStreamReader.getFileFromResource(PROPERTIES_FILE_NAME).toFile()));
        } catch (IOException | ServiceException e) {
            throw new RuntimeException(e);
        }
        databaseUrl = properties.getProperty("db.url");
        for (int i = 0; i < CAPACITY; i++) {
            Connection connection = createConnection(databaseUrl, properties);
            connections.add((ProxyConnection)connection);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (!isCreated.get()) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connections.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void destroyPool() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Логируйте или обрабатывайте исключение здесь
            }
        }
        connections.clear();

        try {
            DriverManager.deregisterDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            // Логируйте или обрабатывайте исключение здесь
        }
    }

    private Connection createConnection(String url, Properties properties) {
        try {
            return new ProxyConnection(DriverManager.getConnection(url, properties));
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
