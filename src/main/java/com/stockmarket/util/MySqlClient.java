package com.stockmarket.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlClient
{
    static Connection connection = null;
    public static Connection getConnection()
    {
        if (connection != null) return connection;
        return getConnection("jdbc:mysql://localhost:3306/stocks", "root", "rootroot");
    }

    private static Connection getConnection(String db_name,String user_name,String password)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(db_name, user_name, password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return connection;
    }
}
