package com.example.serverwork;

import java.sql.*;

public class Loginn {
    public static String Login(UserController.Login userr){
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
            if(rs.next()){

                String login = rs.getString("login");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String role = rs.getString("role");

                // объединяем значения в одну строку
                String result = String.format("User found: login=%s, email=%s, telephone=%s,role=%s", login,email,telephone,role);
                return result;
            }
            else {
                return "User not found"+ userr.Login+userr.Password;
            }
        }catch (Exception e){
            return e.toString();
        }
    }
}
