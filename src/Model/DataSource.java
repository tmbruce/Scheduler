
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSource {
    //Connection Details
    private static final String CONNECTION_STRING = "jdbc:mysql://52.206.157.109/U05SnL";
    private static final String CONNECTION_USERNAME = "U05SnL";
    private static final String CONNECTION_PASSWORD = "53688592736";
    
    //Database Tables/Columns
    //Shared columns
    private static final String COLUMN_CREATE_DATE = "createDate";
    private static final String COLUMN_LAST_UPDATE = "lastUpdate";
    private static final String COLUMN_LAST_UPDATED_BY = "lastUpdatedBy";
    private static final String COLUMN_CUSTOMER_ID = "customerId";
    private static final String COLUMN_COUNTRY_ID = "countryId";
    private static final String COLUMN_ADDRESS_ID = "addressID";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CREATE_BY = "createBy";
    
    //Address table
    private static final String TABLE_ADDRESS = "address";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_ADDRESS2 = "address2";
    private static final String COLUMN_POST_CODE = "postalCode";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_CREATED_BY = "createdBy";
    
    //Appointment table
    private static final String TALBE_APPOINTMENT = "appointment";
    private static final String COLUMN_APPOINTMENT_ID = "appointmentId";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_START = "start";
    private static final String COLUMN_END = "end";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_USER_ID = "userId";
    
    //City table
    private static final String TABLE_CITY = "city";
    private static final String COLUMN_CITY_ID = "cityId";
    private static final String COLUMN_CITY = "city";
    
    
    //Country table
    private static final String TABLE_COUNTRY = "country";
    private static final String COLUMN_COUNTRY = "country";
    
    //Customer table
    private static final String TABLE_CUSTOMER  = "customer";
    private static final String COLUMN_CUSTOMER_NAME = "customerName";
    private static final String COLUMN_ACTIVE = "active";
    
    
    //User table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_NAME = "userName";
    private static final String COLUMN_PASSWORD = "password";

    private Connection conn;
    
    public boolean open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USERNAME, CONNECTION_PASSWORD);
            return true;
        }
        catch(ClassNotFoundException | SQLException ex){
        }
        return false;
    }
    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }
        catch(SQLException e){  
        }
    }
    
    //This is a method to verify if a username exists in the database
    public boolean verifyExistingUser(String checkName, String checkEmail){
        boolean exists = false;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = conn.prepareStatement("SELECT " + COLUMN_USER_NAME + " , " + COLUMN_EMAIL + " FROM " + TABLE_USER +
                                              " WHERE " + COLUMN_USER_NAME + " = " + "\"" + checkName + "\"" + " OR " +
                                              COLUMN_EMAIL + " = " + "\"" + checkEmail + "\"");
            result = statement.executeQuery();
            ArrayList<String> arrayList = new ArrayList<>();
            while(result.next()){
                arrayList.add(result.getString(1));
                }
                if((arrayList.contains(checkName) || (arrayList.contains(checkEmail)))){
                    exists = true;
                }
            }
        catch(SQLException e){
        }
        finally{
            try{
                if (statement != null){
                    statement.close();
                }
            }
            catch (SQLException e){
                }
        }
        return exists;
    }
    public String getTimeStamp(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeStamp = df.format(new Date());
        return timeStamp;
    }
    
    public boolean loginWithEmail(String email, String password){
        boolean exists = false;
        PreparedStatement statement = null;
        ResultSet result = null;
        try{
            statement = conn.prepareStatement("SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL +
                                              " = ?");
            statement.setString(1, email);
            result = statement.executeQuery();
            ArrayList<String> arrayList = new ArrayList<>();
            while(result.next()){
                arrayList.add(result.getString(1));

            }
            if((arrayList.size() == 1) && (arrayList.contains(password))){
                exists = true;
            }
        }
        catch(SQLException e){
            }
        finally{
            try{
                if(statement != null){
                    statement.close();
                }
            }
            catch(SQLException e){
            }
        }                  
        return exists;
    }
    
        public boolean loginWithUserName(String userName, String password){
        boolean exists = false;
        PreparedStatement statement = null;
        ResultSet result = null;
        try{
            statement = conn.prepareStatement("SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME +
                                              " = ?");
            statement.setString(1, userName);
            result = statement.executeQuery();
            ArrayList<String> arrayList = new ArrayList<>();
            while(result.next()){
                
                arrayList.add(result.getString(1));
            }
            if((arrayList.size() == 1) && (arrayList.contains(password))){
                exists = true;

            }
        }
        catch(SQLException e){
            }
        finally{
            try{
                if(statement != null){
                    statement.close();
                }
            }
            catch(SQLException e){
            }
        }                  
        return exists;
    }
    
    public void insertRegistration(String userName, String email, String password){
        PreparedStatement statement;
        try{
            statement = conn.prepareStatement("INSERT INTO " + TABLE_USER + " (" + COLUMN_USER_NAME + ", " + 
                                              COLUMN_PASSWORD + ", " + COLUMN_ACTIVE + ", " + COLUMN_CREATE_BY +
                                              ", " + COLUMN_CREATE_DATE + ", " + COLUMN_LAST_UPDATE + ", " +
                                              COLUMN_LAST_UPDATED_BY + ", " + COLUMN_EMAIL + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.setInt(3, 1);
            statement.setString(4, userName);
            statement.setString(5, getTimeStamp());
            statement.setString(6, getTimeStamp());
            statement.setString(7, userName);
            statement.setString(8, email);
            statement.executeUpdate();
            statement.close();
            }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public String getUserNameFromEmail(String email){
        PreparedStatement statement = null;
        ResultSet result = null;
        String newUserName = null;
        try{
            statement = conn.prepareStatement("SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_USER +
                                              " WHERE " + COLUMN_EMAIL + " = ?");
            statement.setString(1, email);
            result = statement.executeQuery();
            ArrayList<String> userName = new ArrayList<>();
            while(result.next()){
                userName.add(result.getString(1));
            }
            newUserName = userName.get(0);
        }
        catch(SQLException e){
            }
        finally{
            try{
                if(statement != null){
                    statement.close();
                }
            }
            catch(SQLException e){
            }
        }
       return newUserName; 
    }
    
    public ArrayList selectCountries(){
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<String> countryList = new ArrayList<>(); 
        try {
            statement = conn.prepareStatement("SELECT " + COLUMN_COUNTRY + " FROM " + TABLE_COUNTRY);
            result = statement.executeQuery();
            while(result.next()){
                countryList.add(result.getString(1));
            }
            
        } catch (SQLException ex) {
        }
        return countryList;

    }
        public ObservableList<String> selectCities(String countryName){
        PreparedStatement statement = null;
        ResultSet result = null;
        ObservableList<String> countryList = FXCollections.observableArrayList();
        try {
            statement = conn.prepareStatement("SELECT " + COLUMN_CITY + " FROM " + TABLE_CITY + " WHERE " + COLUMN_COUNTRY + 
                                              " = ?");
            statement.setString(1, countryName);
            result = statement.executeQuery();
            while(result.next()){
                countryList.add(result.getString(1));
            }
            
        } catch (SQLException ex) {
        }
        return countryList;

    }
}

