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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private TableView<Appointment> scheduleTableView;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<Appointment, String> timeColumn;
    @FXML
    private TableColumn<Appointment, String> customerColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private ChoiceBox<String> userChoiceBox;
    private User user;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> userAppts = FXCollections.observableArrayList();
    private final ArrayList<String> months = new ArrayList<>();
    @FXML
    private Label numberUsersCustomers;
    @FXML
    private Button numberActiveInactive;
    @FXML
    private ChoiceBox<String> activeInactiveChoiceBox;
    @FXML
    private ChoiceBox<String> userCustomerChoiceBox;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       numberUsersCustomers.setVisible(false);
       numberOfApptsLabel.setVisible(false);
       activeInactiveChoiceBox.getItems().addAll("Active", "Inactive");
       userCustomerChoiceBox.getItems().addAll("Customers", "Users");
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
        String customerName = null;
        String userName = userChoiceBox.getSelectionModel().getSelectedItem();
        int userID = 0;
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getUserName().equalsIgnoreCase(userName)){
                userID = userList.get(i).getUserId();
            }
        }
        for (int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getUserId() == userID){
                for (int j = 0; j < customerList.size(); j++){
                    if(appointmentList.get(i).getCustomerId() == customerList.get(j).getCustomerID()){
                        customerName = customerList.get(j).getCustomerName();
                    }
                }
                userAppts.add(new Appointment(appointmentList.get(i).getStart().toLocalDate().toString(),
                                              appointmentList.get(i).getStart().toLocalTime().toString(),
                                              customerName,
                                              appointmentList.get(i).getType(),
                                              appointmentList.get(i).getLocation(),
                                              appointmentList.get(i).getContact()));                             
            }
        }
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        scheduleTableView.getItems().clear();
        scheduleTableView.getItems().addAll(userAppts);
    }

    @FXML
    private void activeInactiveHandler(ActionEvent event) {
        int displayNum = 0;
        if(activeInactiveChoiceBox.getSelectionModel().getSelectedItem().equals("Active")){
            if(userCustomerChoiceBox.getSelectionModel().getSelectedItem().equals("Customers")){
                for (int i = 0; i < customerList.size(); i++){
                    if(customerList.get(i).getActive() == 1){
                        displayNum++;
                    }     
                }
                numberUsersCustomers.setText(String.valueOf(displayNum));
                numberUsersCustomers.setVisible(true);
            }
            else {
                for (int i = 0; i < userList.size(); i++){
                    if(userList.get(i).getActive() == 1){
                        displayNum++;
                    }
                }
                numberUsersCustomers.setText(String.valueOf(displayNum));
                numberUsersCustomers.setVisible(true);
            }
        }
        if (activeInactiveChoiceBox.getSelectionModel().getSelectedItem().equals("Inactive")){
            if (userCustomerChoiceBox.getSelectionModel().getSelectedItem().equals("Customers")){
            for (int i = 0; i < customerList.size(); i++){
                if (customerList.get(i).getActive() != 1){
                    displayNum++;
                    }
                }
            numberUsersCustomers.setText(String.valueOf(displayNum));
            numberUsersCustomers.setVisible(true);
            }
            else {
                for (int i = 0; i < userList.size(); i++){
                    if (userList.get(i).getActive() != 1){
                        displayNum++;
                    }
                }
                numberUsersCustomers.setText(String.valueOf(displayNum));
                numberUsersCustomers.setVisible(true);
            }
        }
    }    
}
