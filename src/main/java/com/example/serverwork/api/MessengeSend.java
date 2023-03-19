package com.example.serverwork.api;

import com.example.serverwork.servercode.DataBaseConnection;
import com.example.serverwork.servercode.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.*;


public class MessengeSend extends DataBaseConnection {
    public static ObjectNode Send(UserController.Message message){
    try{

        ObjectMapper objectMapper= new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        if(message.getMessage()==null){
            json.put("MessageStatus","message without text");
            return json;
        }
        if (message.getMessage().length()>255){
            json.put("MessageStatus","message too big");
            return json;
        }
        Connection connection = Returnconnection();
        String check = "SELECT * FROM users WHERE login = ?";
        PreparedStatement checkstatement = connection.prepareStatement(check);
        checkstatement.setString(1,message.getGetter());
        ResultSet Isexicted = checkstatement.executeQuery();

        if (Isexicted.next()){
            String sql="INSERT INTO messages(sender,getter,message,read) VALUES (?, ?, ?, false)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,message.getSender());
            statement.setString(2, message.getGetter());
            statement.setString(3,message.getMessage());
            int rowsinserted = statement.executeUpdate();
            statement.close();
            connection.close();
            if(rowsinserted>0){
               json.put("MessageStatus","Sent");
               return json;
            }
            else{
                json.put("MessageStatus","didnt send");
                return json;
            }
        }
        else {
            json.put("MessageStatus","user not found");
            return json;
        }


    }catch (Exception e){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("Error",e.toString());
        return json;
    }
    }
}
