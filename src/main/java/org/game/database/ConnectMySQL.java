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
    
    private static String connectionURL = "jdbc:mysql://localhost:3306/pixel_hunter";

    private static String username = "root";
    private static String password = "";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionURL, username, password);

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
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

    public static void update(String name, double time) {

    }
}
