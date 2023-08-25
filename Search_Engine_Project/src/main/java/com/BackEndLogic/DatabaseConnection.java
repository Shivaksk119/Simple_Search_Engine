package com.BackEndLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;

    public static Connection getConnection() {
        if(connection!=null) {
            return connection;
        }
        String user = "root";
        String pwd = "Chintu#119ss";
        String db = "SearchEngineApp";
        return getConnection(user, pwd, db);
    }

    private static Connection getConnection(String user, String pwd, String db) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+db,user, pwd);// I've used my own menthod instead of this---//3306/"+db+"?user="+user+"&password="+pwd
        }
        catch(SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }
}