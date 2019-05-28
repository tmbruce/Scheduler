/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.DataSource;
import Model.User;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author travi
 */
public class CreateAppointmentController implements Initializable, ControllerInterface {

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField URLfield;
    @FXML
    private ChoiceBox<String> customerDropDown;
    @FXML
    private DatePicker startTime;
    @FXML
    private Spinner starHourSpinner, startMinuteSpinner;    
    @FXML
    private Spinner durationHours, durationMinutes;
    @FXML
    private ComboBox dayNightCombo;
    private Customer customer;
    private User user;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            DataSource datasource = new DataSource();
            datasource.open();
            customerList = datasource.getCustomers();
            datasource.close();
        }
        catch(SQLException e){
        }
        for (int i = 0; i<customerList.size(); i++){
            customerDropDown.getItems().add(customerList.get(i).getCustomerName());
        }
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,0);
        this.durationHours.setValueFactory(hoursFactory);
        SpinnerValueFactory<Integer> minutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        this.durationMinutes.setValueFactory(minutesFactory);
        SpinnerValueFactory<Integer> startHoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,0);
        this.starHourSpinner.setValueFactory(startHoursFactory);
        SpinnerValueFactory<Integer> startMinutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        this.startMinuteSpinner.setValueFactory(startMinutesFactory);
        dayNightCombo.getItems().addAll("AM", "PM");
        
    }
       
    @FXML
    public void saveButtonHandler(ActionEvent event){
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String type = typeField.getText();
        String url = URLfield.getText();
        String cust = customerDropDown.getSelectionModel().getSelectedItem();
        LocalDate appointmentDate = startTime.getValue();
        String startHour = starHourSpinner.getValue().toString();
        String startMinute = startMinuteSpinner.getValue().toString();
        String amPm = dayNightCombo.getValue().toString();
        String durationHour = durationHours.getValue().toString();
        String durationMinute = durationMinutes.getValue().toString();
        
        LocalTime apptStartTime = LocalTime.parse(startHour + ":" + startMinute);
        if(amPm.equals("PM")){
            apptStartTime.plusHours(12);
        }
        LocalTime apptEndTime = apptStartTime;
        apptEndTime.plusHours(Integer.parseInt(durationHour));
        apptEndTime.plusMinutes(Integer.parseInt(durationMinute));

        
        
    }
    
    @FXML
    public void cancelButtonHandler(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }
}
