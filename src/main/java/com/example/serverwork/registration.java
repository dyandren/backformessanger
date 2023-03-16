package com.example.serverwork;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.*;


public class registration extends DataBaseConnection{
    public static ObjectNode Registation(UserController.User userr){
        try{

            Connection connection = Returnconnection();
            String check = "SELECT * FROM users WHERE (login = ?) OR (email = ?)OR(telephone= ?)";
            PreparedStatement checkstatement = connection.prepareStatement(check);
            checkstatement.setString(1,userr.getLogin());
            checkstatement.setString(2,userr.getEmail());
            checkstatement.setString(3,userr.getTelephone());
            ResultSet Isexicted = checkstatement.executeQuery();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode json = objectMapper.createObjectNode();
            if(Isexicted.next()){
                json.put("Regisration status","user with this login exist");
                return json;
            }

            String sql = "INSERT INTO users(login,password,role,email,telephone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userr.getLogin());
            statement.setString(2,userr.getPassword());
            statement.setString(3,userr.getRole());
            statement.setString(4,userr.getEmail());
            statement.setString(5,userr.getTelephone());
            int rowsInserted = statement.executeUpdate();
            statement.close();

            connection.close();
            if(rowsInserted>0){

                json.put("Regisration status","Registration completed");
                return json;

            }
            else {
                json.put("Regisration status","Registration failed");
                return json;
            }
        }catch (Exception e){
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode json = objectMapper.createObjectNode();
            json.put("Error",e.toString());
            return json;        }
    }

}

