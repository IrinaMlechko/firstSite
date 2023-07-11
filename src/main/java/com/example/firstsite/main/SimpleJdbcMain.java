//package com.example.firstsite.main;
//
//import com.example.firstsite.entity.Credentials;
//import com.example.firstsite.pool.ConnectionPool;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SimpleJdbcMain {
//    public static void main(String[] args) {
//
////        try (Connection connection = ConnectionCreator.createConnection();
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             Statement statement = connection.createStatement()) {
//            String sql = "SELECT id, name, password FROM web.users";
//            ResultSet resultSet = statement.executeQuery(sql);
//            List<Credentials> credentials = new ArrayList<>();
//            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString(2);
//                String password = resultSet.getString(3);
//                credentials.add(new Credentials(id, name, password));
//            }
//            System.out.println(credentials);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
