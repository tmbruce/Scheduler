
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String COLUMN_LAST_UPDATED_BY = "lastUpdateBy";
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
    private static final String TABLE_APPOINTMENT = "appointment";
    private static final String COLUMN_APPOINTMENT_ID = "appointmentId";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_CONTACT = "contact";
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
    
    public ObservableList getAppointments() throws SQLException{
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        PreparedStatement statement = null;
        ResultSet result = null;
        String format  = "yyyy-MM-dd HH:mm:ss.S";
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        
        try{
            statement = conn.prepareStatement("SELECT " + COLUMN_APPOINTMENT_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_APPOINTMENT_ID + ", " + COLUMN_CUSTOMER_ID + ", " + COLUMN_TITLE + ", " + COLUMN_DESCRIPTION + ", " +
                                              COLUMN_LOCATION + ", " + COLUMN_TYPE + ", " + COLUMN_CONTACT + ", " + COLUMN_URL  + ", " + COLUMN_START + ", " + COLUMN_END + " FROM " + 
                                              TABLE_APPOINTMENT + " ORDER BY " + COLUMN_START); 
            result = statement.executeQuery();
            while (result.next()){
                Appointment appointment = new Appointment(Integer.parseInt(result.getString(COLUMN_CUSTOMER_ID)),
                                                          Integer.parseInt(result.getString(COLUMN_USER_ID)),
                                                          result.getString(COLUMN_TITLE),
                                                          result.getString(COLUMN_DESCRIPTION),
                                                          result.getString(COLUMN_LOCATION),
                                                          result.getString(COLUMN_CONTACT),
                                                          result.getString(COLUMN_TYPE),
                                                          result.getString(COLUMN_URL),
                                                          TimeShift.UTCtoLocal(LocalDateTime.parse(result.getString(COLUMN_START), df)),
                                                          TimeShift.UTCtoLocal(LocalDateTime.parse(result.getString(COLUMN_END), df)));
                appointment.setAppointmentId(Integer.parseInt(result.getString(COLUMN_APPOINTMENT_ID)));
                appointmentList.add(appointment);
            }
        }
        catch(SQLException e){
        }

        return appointmentList;
    }
    public String getCustomerFromID(int userId) throws SQLException{
        String customerName = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        statement = conn.prepareStatement("SELECT " + COLUMN_CUSTOMER_NAME + " FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMER_ID + " = ?");
        statement.setInt(1, userId);
        result = statement.executeQuery();
        while (result.next()){
            customerName = result.getString(COLUMN_CUSTOMER_NAME);
        }
        return customerName;
    }
    
    public ObservableList getCustomers() throws SQLException{
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        PreparedStatement statement = null;
        ResultSet result = null;

            statement = conn.prepareStatement("SELECT " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID + ", " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_NAME + ", " +
                                              TABLE_ADDRESS + "." + COLUMN_ADDRESS + ", " + TABLE_ADDRESS + "." + COLUMN_ADDRESS2 + ", " +
                                              TABLE_ADDRESS + "." + COLUMN_POST_CODE + ", " + TABLE_CITY + "." + COLUMN_CITY + ", " + TABLE_COUNTRY + "." + COLUMN_COUNTRY + ", " +
                                              TABLE_CUSTOMER + "." + COLUMN_ACTIVE + ", " + TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID + ", " +
                                              TABLE_CUSTOMER + "." + COLUMN_EMAIL + ", " + TABLE_ADDRESS + "." + COLUMN_PHONE + " FROM (((" + TABLE_CUSTOMER +
                                              " INNER JOIN " + TABLE_ADDRESS + " ON " + TABLE_CUSTOMER + "." + COLUMN_ADDRESS_ID + " = " + TABLE_ADDRESS + "." + COLUMN_ADDRESS_ID
                                              + ") INNER JOIN " + TABLE_CITY + " ON " + TABLE_ADDRESS + "." + COLUMN_CITY_ID + " = " + TABLE_CITY + "." + COLUMN_CITY_ID + ") " +
                                              " INNER JOIN " + TABLE_COUNTRY + " ON " + TABLE_CITY + "." + COLUMN_COUNTRY_ID + " = " + TABLE_COUNTRY + "." + COLUMN_COUNTRY_ID + ")");
            result = statement.executeQuery();
            
            while (result.next()){
                Customer customer = new Customer(result.getString(COLUMN_CUSTOMER_NAME),
                                                 result.getString(COLUMN_EMAIL),                         
                                                 result.getString(COLUMN_PHONE),
                                                 result.getInt(COLUMN_ADDRESS_ID),
                                                 result.getInt(COLUMN_ACTIVE),                         
                                                 result.getInt(COLUMN_CUSTOMER_ID),
                                                 result.getString(COLUMN_ADDRESS),
                                                 result.getString(COLUMN_ADDRESS2),
                                                 result.getInt(COLUMN_POST_CODE),
                                                 result.getString(COLUMN_CITY),
                                                 result.getString(COLUMN_COUNTRY));
                customerList.add(customer);
            }

        return customerList;
    }
    public void updateAppointment(int customerId, String title, String description, String location, String contact, String url, LocalDateTime start, LocalDateTime end,
                                  String type, User user, Appointment appointment) throws SQLException{
        PreparedStatement statement = null;
        
        //FIXME - need to remove referential integrity issue, remove (SELECT userId FROM user WHEERE userName = ... Creates SQL exception when user edits an appointment they didn't create
        statement = conn.prepareStatement("UPDATE " + TABLE_APPOINTMENT + " SET " + COLUMN_CUSTOMER_ID + " = ?, " + COLUMN_TITLE + " = ?, " + COLUMN_DESCRIPTION + " = ?, " + COLUMN_LOCATION + 
                                          " =  ?, " + COLUMN_CONTACT + " = ?, " + COLUMN_URL + " = ?, " + COLUMN_START + " = ?, " + COLUMN_END + " = ?, " + COLUMN_LAST_UPDATE + " = ?, " +
                                          COLUMN_LAST_UPDATED_BY + " = ?, " + COLUMN_TYPE + " = ? WHERE appointmentId = ?");
        statement.setInt(1, customerId);
        statement.setString(2, title);
        statement.setString(3, description);
        statement.setString(4, location);
        statement.setString(5, contact);
        statement.setString(6, url);
        statement.setString(7, TimeShift.localToUTC(start).toString());
        statement.setString(8, TimeShift.localToUTC(end).toString());
        statement.setString(9, getTimeStamp());
        statement.setString(10, user.getUserName());
        statement.setString(11, type);
        statement.setInt(12, appointment.getAppointmentId());
        statement.executeUpdate();
        statement.close();   
    }
    
    public void insertAppointment(int customerId, String title, String description, String location, String contact, String url, LocalDateTime start, LocalDateTime end,
                                  String type, User user) throws SQLException {
        PreparedStatement statement = null;
  
        statement = conn.prepareStatement("INSERT INTO " + TABLE_APPOINTMENT + " (" + COLUMN_CUSTOMER_ID + ", " + COLUMN_TITLE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_LOCATION + ", " +  
                COLUMN_CONTACT + ", " + COLUMN_URL + ", " + COLUMN_START + ", " + COLUMN_END + ", " + COLUMN_CREATE_DATE + ", " + COLUMN_CREATED_BY + ", " + COLUMN_LAST_UPDATE +
                              ", " + COLUMN_LAST_UPDATED_BY + ", " + COLUMN_TYPE + ", " + COLUMN_USER_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                                      + "(SELECT userId FROM user WHERE userName = ?))");
        statement.setInt(1, customerId);
        statement.setString(2, title);
        statement.setString(3, description);
        statement.setString(4, location);
        statement.setString(5, contact);
        statement.setString(6, url);
        statement.setString(7, TimeShift.localToUTC(start).toString());
        statement.setString(8, TimeShift.localToUTC(end).toString());
        statement.setString(9, getTimeStamp());
        statement.setString(10, user.getUserName());
        statement.setString(11, getTimeStamp());
        statement.setString(12, user.getUserName());
        statement.setString(13, type);
        statement.setString(14, user.getUserName());
        statement.executeUpdate();
        statement.close();
    }
    
    public void deleteAppointment(Appointment appointment, User user) throws SQLException{
        PreparedStatement statement = null;
        statement = conn.prepareStatement("DELETE FROM " + TABLE_APPOINTMENT + " WHERE " + COLUMN_APPOINTMENT_ID + " = ?");
        statement.setInt(1, appointment.getAppointmentId());
        statement.executeUpdate();
        statement.close();
    }
    
    public void insertCustomer(String customerName, String email, String customerPhone, String streetAddress, String postCode, String city, String country, User user) throws SQLException{
        //Using transaction state
        conn.setAutoCommit(false);
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;

        statement = conn.prepareStatement("INSERT INTO " + TABLE_ADDRESS + " (" + COLUMN_ADDRESS + ", " + COLUMN_ADDRESS2 + ", " + 
                                          COLUMN_CITY_ID + ", " + COLUMN_POST_CODE + ", " + COLUMN_PHONE + ", " + COLUMN_CREATE_DATE + ", " +
                                          COLUMN_CREATED_BY + ", " + COLUMN_LAST_UPDATE + ", " + COLUMN_LAST_UPDATED_BY +
                                          ") VALUES (?, ?, (SELECT cityId FROM city WHERE city = ?), ?, ?, ?, ?, ?, ?)");
        statement.setString(1, streetAddress);
        statement.setString(2, city);
        statement.setString(3, city);
        statement.setString(4, postCode);
        statement.setString(5, customerPhone);
        statement.setString(6, getTimeStamp());
        statement.setString(7, user.getUserName());
        statement.setString(8, getTimeStamp());
        statement.setString(9, user.getUserName());
        statement.executeUpdate();
        statement.close();

        statement2 = conn.prepareStatement("INSERT INTO " + TABLE_CUSTOMER + " (" + COLUMN_CUSTOMER_NAME + ", " + COLUMN_ADDRESS_ID + ", " +
                           COLUMN_ACTIVE + ", " + COLUMN_CREATE_DATE + ", " + COLUMN_CREATED_BY + ", " + COLUMN_LAST_UPDATE + ", " + COLUMN_LAST_UPDATED_BY + ", " +
                           COLUMN_EMAIL + ") VALUES (?, (SELECT LAST_INSERT_ID()), ?, ?, ?, ?, ?, ?)");
        statement2.setString(1, customerName);
        statement2.setInt(2, 1);
        statement2.setString(3, getTimeStamp());
        statement2.setString(4, user.getUserName());
        statement2.setString(5, getTimeStamp());
        statement2.setString(6, user.getUserName());
        statement2.setString(7, email);
        statement2.executeUpdate();
        statement2.close();

        conn.commit();
        conn.setAutoCommit(true);

    }
    public void deleteCustomer(Customer customer, User user) throws SQLException{
        conn.setAutoCommit(false);
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        
        statement = conn.prepareStatement("DELETE FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMER_ID + " = ?");
        statement.setString(1, Integer.toString(customer.getCustomerID()));
        statement.executeUpdate();
        statement.close();
        
        statement2 = conn.prepareStatement("DELETE FROM " + TABLE_ADDRESS + " WHERE " + COLUMN_ADDRESS_ID + " = ?");
        statement2.setString(1, Integer.toString(customer.getAddressID()));
        statement2.executeUpdate();
        statement2.close();
        conn.commit();
        conn.setAutoCommit(true);
    }
    
    public void updateCustomer(String customerName, String email, String customerPhone, String streetAddress,
                                   String postCode, String city, String country, int active, User user, Customer customer) throws SQLException{
        //Using transaction state
        conn.setAutoCommit(false);
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try{
            statement = conn.prepareStatement("UPDATE " + TABLE_ADDRESS + " SET " + COLUMN_ADDRESS + " = ?, " + COLUMN_ADDRESS2 + " = ?, " + COLUMN_CITY_ID 
                                              + " = (SELECT cityId FROM city WHERE city = ?), " + COLUMN_POST_CODE + " = ?, " + COLUMN_PHONE + " = ?, " +
                                              COLUMN_LAST_UPDATE + " = ?, " + COLUMN_LAST_UPDATED_BY + " = ? WHERE " + COLUMN_ADDRESS_ID + " = ?");

            statement.setString(1, streetAddress);
            statement.setString(2, city);
            statement.setString(3, city);
            statement.setString(4, postCode);
            statement.setString(5, customerPhone);
            statement.setString(6, getTimeStamp());
            statement.setString(7, user.getUserName());
            statement.setString(8, Integer.toString(customer.getAddressID()));
            statement.executeUpdate();
            statement.close();
            
            statement2 = conn.prepareStatement("UPDATE " + TABLE_CUSTOMER + " SET " + COLUMN_CUSTOMER_NAME + " = ?, " +  COLUMN_ADDRESS_ID + " = (SELECT addressId FROM address WHERE address = ?), "
                                               + COLUMN_ACTIVE + " = ?, " + COLUMN_LAST_UPDATE + " = ?, " + COLUMN_LAST_UPDATED_BY + " = ?, " + COLUMN_EMAIL + " = ? WHERE " +
                                               COLUMN_CUSTOMER_NAME + " = ?");
            statement2.setString(1, customerName);
            statement2.setString(2, streetAddress);
            statement2.setInt(3, active);
            statement2.setString(4, getTimeStamp());
            statement2.setString(5, user.getUserName());
            statement2.setString(6, email);
            statement2.setString(7, customer.getCustomerName());
            statement2.executeUpdate();
            statement2.close();
            conn.commit();
            conn.setAutoCommit(true);
        }
        catch(SQLException e){
        }
    }
    
    public ObservableList getUsers() throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
        ObservableList<User> userList = FXCollections.observableArrayList();
        
        statement = conn.prepareStatement("SELECT " + COLUMN_USER_NAME + ", " + COLUMN_USER_ID + ", " + COLUMN_ACTIVE + " FROM " + TABLE_USER);
        result = statement.executeQuery();
        
        while(result.next()){
            User user = new User(result.getString(COLUMN_USER_NAME),
                                 result.getInt(COLUMN_USER_ID),
                                 result.getInt(COLUMN_ACTIVE));
            userList.add(user);
            
            
        }
        statement.close();
        return userList;
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
            statement.close();
        } 
        catch (SQLException ex) {
        }
        return countryList;

    }
        public ArrayList<String> selectCities(String countryName){
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<String> cityList = new ArrayList<>();
        try {
            statement = conn.prepareStatement("SELECT " + COLUMN_CITY + " FROM " + TABLE_CITY + " WHERE " + COLUMN_COUNTRY_ID + 
                                              " = (SELECT " + COLUMN_COUNTRY_ID + " FROM " + TABLE_COUNTRY + " WHERE " + COLUMN_COUNTRY + 
                                              " = ?)");
            statement.setString(1, countryName);
            result = statement.executeQuery();
            while(result.next()){
                cityList.add(result.getString(1));
            }
            statement.close();
        } 
        catch (SQLException ex) {
            }
        return cityList;
    }
        
        public ArrayList getLocations(){
            PreparedStatement statement = null;
            ResultSet result = null;
            ArrayList<ArrayList<String>> countryList = new ArrayList<>();
            try {
                statement = conn.prepareStatement("SELECT " + TABLE_COUNTRY + "." + COLUMN_COUNTRY + ", " + TABLE_CITY + "." + COLUMN_CITY + " FROM (" +
                                                  TABLE_CITY + " INNER JOIN " + TABLE_COUNTRY + " ON " + TABLE_CITY + "." + COLUMN_COUNTRY_ID + " = " + 
                                                  TABLE_COUNTRY + "." + COLUMN_COUNTRY_ID + ")");
                result = statement.executeQuery();
                while(result.next()){
                    countryList.add(new ArrayList<>(Arrays.asList(result.getString(COLUMN_COUNTRY), result.getString(COLUMN_CITY))));                    
                }
                statement.close();
            } 
            catch (SQLException ex) {
            }
            return countryList;
        }
}

