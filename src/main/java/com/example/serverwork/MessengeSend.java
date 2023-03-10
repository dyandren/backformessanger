package com.example.serverwork;

import java.sql.*;


public class MessengeSend {
    public static Boolean Send(UserController.Message message){
    try{
        String url = "jdbc:postgresql://localhost:5432/messenger";
        String user = "postgres";
        String password = "26463";
        Connection connection = DriverManager.getConnection(url,user,password);
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
            if (rowsinserted>0){
               return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }


    }catch (Exception e){
        return false;
    }
    }
}
