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
    private static String DATABASE_URL;
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
        } catch (IOException e) {
            e.printStackTrace(); // fatal exception
        }
        catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        DATABASE_URL = (String) properties.get("db.url");
        for (int i = 0; i < CAPACITY; i++) {
            Connection connection = createConnection(DATABASE_URL, properties);
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
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try{
        connections.put((ProxyConnection) connection);} catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void destroyPool() {
        for (int i = 0; i < CAPACITY; i++) {
            try {
                connections.take().reallyClose();
            } catch (InterruptedException e) {
                /// log e.printStackTrace();
            }
        }
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    private Connection createConnection(String url, Properties properties) {
        Connection proxyConnection;
        try {
            proxyConnection = new ProxyConnection(DriverManager.getConnection(url, properties));
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
        return proxyConnection;

    }
}
