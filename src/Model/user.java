/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.Instant;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author travi
 */
public class user {
    private SimpleStringProperty userName;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private Locale userCountry;
    private Instant userTime;
    
    public user(String userName, String password, String email){
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);        
    }
    public Locale getLocale(){
        return Locale.getDefault();
    }
    public void getLocale(Locale userCountry){
        this.userCountry = userCountry;
    }
    
    public String getUserName(){
        return userName.get();
    }
    public void setUserName(SimpleStringProperty userName){
        this.userName = userName;
    }
    public String getEmail(){
        return email.get();
    }
    public void setEmail(SimpleStringProperty email){
        this.email = email;
    }
    public String getPassword(){
        return password.get();
    }
    public void setPassword(SimpleStringProperty password){
        this.password = password;
    }
    
    //Validation methods
    
    //validate matching password entries
    public static boolean validatePassword(String password, String password2){
        return (password == null ? password2 == null : password.equals(password2) && (password.length() >= 4));
    }
    //simple validation - username greater than or equal to 4 characters
    
    public static boolean validateUserName(String username){
        return username.length() >= 4;
    }
    //email validation
    public static boolean validateEmailAddress(String emailAddress){
        return (emailAddress.contains("@")) && (emailAddress.contains("."));
    }
    
    
    
    
}
