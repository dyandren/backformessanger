package com.example.serverwork;

import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageGet {
    public static JSONObject Get(UserController.Messageget messageget){
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
            JSONObject json = new JSONObject();
            JSONArray contactsArray = new JSONArray();
            while (rs.next()){
                contactsArray.put(rs.getString("contact"));
            }
            json.put("contacts",contactsArray);
            if(messageget.getSelection()!=0){
                json = new JSONObject();
                String whatUserSelect = contactsArray.getString(messageget.getSelection() - 1);
                sql = "SELECT * FROM messages WHERE sender = ? or getter = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,whatUserSelect);
                preparedStatement.setString(2,whatUserSelect);
                rs = preparedStatement.executeQuery();
                JSONArray messages = new JSONArray();
                while(rs.next()){
                    JSONObject whatInside = new JSONObject();
                    whatInside.put("ID",rs.getInt("id"));
                    whatInside.put("sender",rs.getString("sender"));
                    whatInside.put("getter",rs.getString("getter"));
                    whatInside.put("message",rs.getString("message"));
                    whatInside.put("date",rs.getDate("senddate"));
                    whatInside.put("read",rs.getBoolean("read"));
                    messages.put(whatInside);
                }
                json.put("messages",messages);

                return json;


            }

            return json;


        }catch (Exception e){
            JSONObject error = new JSONObject();
            error.put("Error",e);
            return error;
        }
    }
}
