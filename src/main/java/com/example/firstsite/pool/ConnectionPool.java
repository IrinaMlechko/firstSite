package com.example.firstsite.pool;

import com.example.firstsite.exception.ServiceException;
import com.example.firstsite.reader.PropertiesStreamReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
    private BlockingQueue<Connection> connections = new LinkedBlockingQueue<>(CAPACITY);
    private BlockingQueue<Connection> usedConnections = new LinkedBlockingQueue<>(CAPACITY);
    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
//        try {
// //           properties.load(new FileReader(propertiesStreamReader.getFileFromResource(PROPERTIES_FILE_NAME).toFile()));
//        } catch (IOException e) {
//            e.printStackTrace(); // fatal exception
//        }
//        catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }
//        DATABASE_URL = (String) properties.get("db.url");
        DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
        properties.put("user", "postgres");
        properties.put("password", "postgres");
        for (int i = 0; i < CAPACITY; i++) {
            try {
               Connection connection = DriverManager.getConnection(DATABASE_URL, properties);
               connections.add(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                instance = new ConnectionPool();
                } catch (Exception e) {
                throw new RuntimeException(e);
            }
         finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        connections.add(connection);
    }

    public void destroyPool() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //log
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
}
