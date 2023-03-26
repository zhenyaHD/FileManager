package org.example;

import java.sql.*;

public class Connectivity {

    private final Connection connection;

    public Connectivity() {
        this.connection = Connectivity.getMySqlConnection();
    }

    public static Connection getMySqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            String url = "jdbc:mysql://localhost/servlet?user=root&password=12345";
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

}