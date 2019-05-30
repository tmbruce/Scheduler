/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.DataSource;
import Model.User;
import Model.Appointment;
import Model.TimeShift;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javafx.util.converter.LocalTimeStringConverter;


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
    private Spinner startTimeSpinner;    
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
        
        dayNightCombo.getItems().addAll("AM","PM");
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,0);
        durationHours.setValueFactory(hoursFactory);
        SpinnerValueFactory<Integer> minutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00,59,0);
        durationMinutes.setValueFactory(minutesFactory);

        String pattern = "hh:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        SpinnerValueFactory value = new SpinnerValueFactory<LocalTime>() {
        {
        setConverter(new LocalTimeStringConverter(formatter, null));
        }
        @Override
        public void decrement(int steps) {
            if (getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.minusMinutes(steps));
            }
        }

        @Override
        public void increment(int steps) {
            if (this.getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.plusMinutes(steps));
            }
        }
    };
        startTimeSpinner.setValueFactory(value);

        
    }
       
    @FXML
    public void saveButtonHandler(ActionEvent event)throws SQLException {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String type = typeField.getText();
        String url = URLfield.getText();
        String cust = customerDropDown.getSelectionModel().getSelectedItem();
        LocalDate appointmentDate = startTime.getValue();
        String startTimeValue = startTimeSpinner.getValue().toString();
        String amPm = dayNightCombo.getValue().toString();
        String durationHour = durationHours.getValue().toString();
        String durationMinute = durationMinutes.getValue().toString();
        LocalTime apptStartTime = LocalTime.parse(startTimeValue);
        if(amPm.equalsIgnoreCase("AM")){
            apptStartTime = apptStartTime.minusHours(12);
        }
        int custId = 0;
        for (int i = 0; i < customerList.size(); i++){
            if (customerList.get(i).getCustomerName().equals(cust)){
                custId = customerList.get(i).getCustomerID();
            }
        }

        LocalTime apptEndTime = apptStartTime;
        apptEndTime = apptEndTime.plusHours(Integer.parseInt(durationHour));
        apptEndTime = apptEndTime.plusMinutes(Integer.parseInt(durationMinute));
        LocalDateTime ldtStart = TimeShift.dateTimeBuilder(appointmentDate, apptStartTime);
        LocalDateTime ltdEnd = TimeShift.dateTimeBuilder(appointmentDate, apptEndTime);
        if((Appointment.validateAppointment(title, description, location, contact, type, url)) &&
                Appointment.validateAppointmentTime(ldtStart, ltdEnd)){
            System.out.println("APPOINTMENT ACCEPTED");
//            DataSource datasource = new DataSource();
//            datasource.open();
//            datasource.insertAppointment(custId, title, description, location, contact, url, ldtStart, ltdEnd, type, user);
//            datasource.close();
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

        
        
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
