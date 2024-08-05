package org.game.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.mysql.cj.exceptions.UnableToConnectException;

public final class ConnectMySQL {
    
    private static final String connectionURL = "jdbc:mysql://localhost:3306/pixel_hunter";

    private static final String username = "root";
    private static final String password = "";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionURL, username, password);
            return conn;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static LinkedList<String[]> query() {
        String query = "SELECT * FROM player ORDER BY times";
        Connection conn = connect();
        
        LinkedList<String[]> list = new LinkedList<>();


        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while(rs.next()) {
                String[] s = new String[2];
                s[0] = rs.getString("name");
                s[1] = String.format("%.2f", rs.getDouble("times"));

                list.add(s);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static double query(String name) {
        String query = "SELECT * FROM player WHERE name = ?";
        Connection conn = connect();
        
        double times = 1e9 + 5;

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                return rs.getDouble("times");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return times;
    }

    public static void insert(String name, double times) {
        String query = "INSERT INTO player (name, times) VALUES (?, ?)";
        Connection conn = connect();
        try {
            
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, name);
            stm.setDouble(2, times);

            if (stm.executeUpdate() <= 0) {
                throw new UnableToConnectException();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(String name, double times) {
        String query  = "UPDATE player SET times = ? WHERE name = ?";
        Connection conn = connect();

        try {
            
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setDouble(1, times);
            stm.setString(2, name);

            if (stm.executeUpdate() <= 0) {
                throw new UnableToConnectException();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
