package com.example.serverwork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CreateGroup extends DataBaseConnection {
    public static Object Groupcreation(UserController.GroupCreation groupCreation){
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode json = objectMapper.createObjectNode();
            Connection connection = Returnconnection();
            String check = "SELECT * FROM groups WHERE (name = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(check);
            preparedStatement.setString(1,groupCreation.getGroupname());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                json.put("Group add status","This group name already exist");
                return json;
            }
            String sql = "INSERT INTO groups (name, adminname, users, private) VALUES (?, ?, ARRAY[?], ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,groupCreation.getGroupname());
            preparedStatement.setString(2,groupCreation.getUserwhocreate());
            preparedStatement.setString(3,groupCreation.getUserwhocreate());
            preparedStatement.setBoolean(4,Boolean.parseBoolean(groupCreation.getPrivatee()));

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();

            connection.close();
            if(rowsInserted>0){

                json.put("Group add status","Add completed");
                return json;

            }
            else {
                json.put("Group add status","Add failed");
                return json;
            }


        }catch (Exception e){
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode json = objectMapper.createObjectNode();
            json.put("Error",e.toString());
            return json;         }
    }
}
