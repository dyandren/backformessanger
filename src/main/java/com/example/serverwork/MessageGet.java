package com.example.serverwork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageGet {
    public static Object Get(UserController.Messageget messageget){
        try{
            String url = "jdbc:postgresql://localhost:5432/messenger";
            String user = "postgres";
            String password = "26463";
            Connection connection = DriverManager.getConnection(url,user,password);
            String sql = "SELECT DISTINCT contact\n" +
                    "FROM (\n" +
                    "    SELECT sender AS contact FROM messages WHERE sender <> ? and getter = ?\n" +
                    "    UNION\n" +
                    "    SELECT getter AS contact FROM messages WHERE getter <> ? and sender = ?\n" +
                    ") AS temp_table;";//search a unique contact
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,messageget.getUser());
            preparedStatement.setString(2, messageget.getUser());
            preparedStatement.setString(3, messageget.getUser());
            preparedStatement.setString(4, messageget.getUser());
            ResultSet rs =preparedStatement.executeQuery();
            ArrayList<String> result = new ArrayList<String>();
            while (rs.next()){
                result.add(rs.getString("contact"));
            }
            if(messageget.getSelection()!=0){
                String whatUserSelect = result.get(messageget.getSelection()-1);
                sql = "SELECT * FROM messages WHERE sender = ? or getter = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,whatUserSelect);
                preparedStatement.setString(2,whatUserSelect);
                rs = preparedStatement.executeQuery();
                result = new ArrayList<String>();
                while(rs.next()){
                    String sender = rs.getString("sender");
                    String getter = rs.getString("getter");
                    String message = rs.getString("message");
                    String senddate=rs.getString("senddate");
                    String read = rs.getString("read");
                    result.add(String.format("Message=%s , sender =%s, getter=%s, send date =%s, read = %s \n",message,sender,getter,senddate,read));
                }
                return result;


            }
            return result;

        }catch (Exception e){
        return e;
        }
    }
}
