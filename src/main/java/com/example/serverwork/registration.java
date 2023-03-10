package com.example.serverwork;
import java.sql.*;


public class registration {
    public static String Registation(UserController.User userr){
        try{
            String url = "jdbc:postgresql://localhost:5432/messenger";
            String user = "postgres";
            String password = "26463";
            Connection connection = DriverManager.getConnection(url,user,password);
            String check = "SELECT * FROM users WHERE (login = ?) OR (email = ?)OR(telephone= ?)";
            PreparedStatement checkstatement = connection.prepareStatement(check);
            checkstatement.setString(1,userr.getLogin());
            checkstatement.setString(2,userr.getEmail());
            checkstatement.setString(3,userr.getTelephone());
            ResultSet Isexicted = checkstatement.executeQuery();
            if(Isexicted.next()){
                return "ERROR";
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

                return "Registration complete";
            }
            else {
                return "Registration Failed";
            }
        }catch (Exception e){
            return e.toString();
        }
    }

}

