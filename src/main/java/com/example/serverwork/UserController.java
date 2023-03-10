package com.example.serverwork;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import java.security.PublicKey;


@RestController
public class UserController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring Boot!";
    }

    @PostMapping("/user/registration")
    public ObjectNode createUser(@RequestBody User user) {
        try{
            return registration.Registation(user);
        }catch (Exception e){
            ObjectNode error = new ObjectMapper().createObjectNode();
            error.put("Error", e.toString());
            return error;
        }

    }
    @GetMapping("/user/login")
    public Object Login(@RequestBody Login login){
        try{
    return Loginn.Login(login);
        }catch (Exception e ){
            ObjectNode error = new ObjectMapper().createObjectNode();
            error.put("Error", e.toString());
            return error;
        }
    }
    @PostMapping("/message/send")
    public  ObjectNode Messagesend(@RequestBody Message message){
        try{
            return  MessengeSend.Send(message);
        }
        catch (Exception e){
            ObjectNode error = new ObjectMapper().createObjectNode();
            error.put("Error", e.toString());
            return error;        }
    }
    @GetMapping(value = "/message/get",produces = "application/json")
    public ObjectNode Getmessages(@RequestBody Messageget messageget){
        try {

            return MessageGet.Get(messageget);
        }
        catch (Exception e){
            ObjectNode error = new ObjectMapper().createObjectNode();
            error.put("Error", e.toString());
            return error;
        }
    }
    @PostMapping(value = "/group/create")
    public Object CreateGroup(@RequestBody GroupCreation groupCreation){
        return CreateGroup.Groupcreation(groupCreation);
    }

    public static class User {
        private String login;
        private String email;
        private String password;
        private String role = "casual";
        private String telephone;


        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public  void setPassword(String password){
            this.password=password;
        }
        public String getPassword(){
            return  password;
        }
        public String getRole(){
            return role;
        }
        public  void setTelephone(String telephone){
            this.telephone=telephone;
        }
        public String getTelephone(){
            return  telephone;
        }

    }
    public static class Login{
        String Login;
        String Password;
        public void Login(){

        }
        public void setLogin(String login){
            this.Login=login;
        }
        public String getLogin(){
            return Login;
        }
        public  void setPassword(String password){
            this.Password=password;
        }
        public String getPassword(){
            return Password;
        }
    }
    public static class Message{
        String sender;
        String getter;
        String message;
        public void Message(){

        }

        public void setGetter(String getter) {
            this.getter = getter;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getGetter() {
            return getter;
        }

        public String getMessage() {
            return message;
        }

        public String getSender() {
            return sender;
        }
    }
    public static class Messageget{
        String user;
        int selection;

        public void Messageget(){

        }
        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public int getSelection() {
            return selection;
        }

        public void setSelection(int selection) {
            this.selection = selection;
        }
    }
    public static class GroupCreation{
        String userwhocreate;
        String groupname;
        String privatee;

        public void GroupCreation(){

        }

        public String getGroupname() {
            return groupname;
        }

        public String getUserwhocreate() {
            return userwhocreate;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public void setUserwhocreate(String userwhocreate) {
            this.userwhocreate = userwhocreate;
        }

        public String getPrivatee() {
            return privatee;
        }

        public void setPrivatee(String privatee) {
            this.privatee = privatee;
        }
    }
}
