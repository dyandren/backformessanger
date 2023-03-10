package com.example.serverwork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.*;

public class Loginn {
    public static ObjectNode Login(UserController.Login userr){
        try{
            String url = "jdbc:postgresql://localhost:5432/messenger";
            String user = "postgres";
            String password = "26463";
            String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt= connection.prepareStatement(sql);
            pstmt.setString(1,userr.getLogin());


            pstmt.setString(2,userr.getPassword());
            ResultSet rs = pstmt.executeQuery();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode json = objectMapper.createObjectNode();

            if(rs.next()){
                json.put("login",rs.getString("login"));
                json.put("email",rs.getString("email"));
                json.put("telephone",rs.getString("telephone"));
                json.put("role",rs.getString("role"));
                return json;
            }
            else {
                json.put("Error","User not found");
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
