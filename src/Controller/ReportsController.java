/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Customer;
import Model.DataSource;
import Model.SceneChanger;
import Model.TimeShift;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author travi
 */
public class ReportsController implements Initializable, ControllerInterface {

    @FXML
    private Button calendarButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private AnchorPane calendarAnchor;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<String> monthChoiceBox;
    @FXML
    private Label numberOfApptsLabel;
    @FXML
    private TableView<String> scheduleTableView;
    @FXML
    private TableColumn<String, String> dateColumn;
    @FXML
    private TableColumn<String, String> timeColumn;
    @FXML
    private TableColumn<String, String> customerColumn;
    @FXML
    private TableColumn<String, String> typeColumn;
    @FXML
    private TableColumn<String, String> locationColumn;
    @FXML
    private TableColumn<String, String> contactColumn;
    @FXML
    private ChoiceBox<String> userChoiceBox;
    private User user;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private final ArrayList<String> months = new ArrayList<>();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       numberOfApptsLabel.setVisible(false);
       monthChoiceBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
       typeChoiceBox.getItems().addAll("Sales", "Innovation", "Status Update", "Strategy", "Customer Outreach");
       reportsButton.requestFocus();
       DataSource datasource = new DataSource();
       datasource.open();
        try {
            customerList = datasource.getCustomers();
            appointmentList = datasource.getAppointments();
            userList = datasource.getUsers();
            datasource.close();
        } 
        catch (SQLException ex) {
        }
        
        userList.forEach(_user ->{
            userChoiceBox.getItems().add(_user.getUserName());
        });
        
    }   
    
    //Anonymous class for use in setting table
    public class tableAppointment{
        private String date;
        private String time;
        private String customer;
        private String type;
        private String location;
        private String contact;
        
        public tableAppointment(String date, String time, String customer, String type, String location, String contact){
            this.date = date;
            this.time = time;
            this.customer = customer;
            this.type = type;
            this.location = location;
            this.contact = contact;
        }
        
        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getCustomer() {
            return customer;
        }

        public String getType() {
            return type;
        }

        public String getLocation() {
            return location;
        }

        public String getContact() {
            return contact;
        }
        
    }

    @FXML
    public void customersButtonHandler(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        CustomerController customerController = new CustomerController();
        sc.changeScenes(event, "/Views/Customers.fxml", "CalendarOne - Customers", user, customerController);  
        customersButton.requestFocus();
    }    

    @FXML
    private void calendarButtonHandler(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        MainController mc = new MainController();
        sc.changeScenes(event, "/Views/Main.fxml", "CalendarOne", user, mc);
    }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }

    @FXML
    private void getNumberofAppts(ActionEvent event) {
        numberOfApptsLabel.setVisible(true);
        int numberOfAppts = 0;
        String month = monthChoiceBox.getSelectionModel().getSelectedItem();
        String type = typeChoiceBox.getSelectionModel().getSelectedItem();
        for(int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getStart().getMonth().toString().equalsIgnoreCase(month) && 
                    (appointmentList.get(i).getType().equalsIgnoreCase(type))){
                numberOfAppts++;
            }
        }
        numberOfApptsLabel.setText(String.valueOf(numberOfAppts));
    }

    @FXML
    private void getSchedule(ActionEvent event) {
        ArrayList<ArrayList<String>> userAppts = new ArrayList<>();
        String userName = userChoiceBox.getSelectionModel().getSelectedItem();
        int userID = 0;
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getUserName().equalsIgnoreCase(userName)){
                userID = userList.get(i).getUserId();
            }
        }
        for (int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getUserId() == userID){
                userAppts.add(new ArrayList<String>()
                );
            }
        }
    }
}
