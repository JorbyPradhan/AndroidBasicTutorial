package com.example.onlinequiz.databaseConnection;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    static Connection conn = null;
    static String ConnUrl = null;
    static String ip = "192.168.1.4";

    private DBConnection() {
        String user = "sa";
        String password = "Jamespdh";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnUrl = "jdbc:jtds:sqlserver:/" + ip + "/tester;user=" + user + ";password=" + password + ";";
            conn = DriverManager.getConnection(ConnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /* public static Connection DbCon(){

         return conn;
     }*/
    private static DBConnection connection = null;

    public static DBConnection getInstance() {
        if (connection == null) {
            connection = new DBConnection();
        } else connection = new DBConnection();
        return connection;
    }

    public boolean insertData(String query) {

        try {
            Statement statement = conn.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public ResultSet getData(String query) {
        try {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}