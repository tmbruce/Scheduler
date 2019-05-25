/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.Instant;
import java.util.Locale;


/**
 *
 * @author travi
 */
public class User {
    private String userName;
    private String password;
    private String email;
    private Locale userCountry;
    private Instant userTime;
    
    public User(String userName){
        this.userName = new String(userName);        
    }
    public Locale getLocale(){
        return Locale.getDefault();
    }
    public void getLocale(Locale userCountry){
        this.userCountry = userCountry;
    }
    
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    //Validation methods
    
    //validate matching password entries
    public static boolean validatePassword(String password, String password2){
        return (password == null ? password2 == null : password.equals(password2) && (password.length() >= 4) && (!password.contains("@")));
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