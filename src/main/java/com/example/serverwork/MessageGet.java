package com.example.serverwork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MessageGet {
    public static Object Get(UserController.Messageget messageget){
        try{
            String url = "jdbc:postgresql://localhost:5432/messenger";
            String user = "postgres";
            String password = "26463";
            Connection connection = DriverManager.getConnection(url,user,password);
            String sql = "SELECT * FROM messages WHERE (sender = ?)OR(getter = ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,messageget.getUser());
            preparedStatement.setString(2, messageget.getUser());
            ResultSet rs =preparedStatement.executeQuery();
            String result = "";
            while (rs.next()){
                String sender = rs.getString("sender");
                String getter = rs.getString("getter");
                String message = rs.getString("message");
                String senddate=rs.getString("senddate");
                String read = rs.getString("read");
                 result += String.format("Message=%s , sender =%s, getter=%s, send date =%s, read = %s \n",message,sender,getter,senddate,read);

            }
            return result;

        }catch (Exception e){
        return e;
        }
    }
}
