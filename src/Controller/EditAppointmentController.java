/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.CustomerController.customerList;
import Model.Appointment;
import Model.Customer;
import Model.DataSource;
import Model.TimeShift;
import Model.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

/**
 * FXML Controller class
 *
 * @author tbruce
 */
public class EditAppointmentController implements Initializable, ControllerInterfaceApp {

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField contactField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextField URLfield;
    @FXML
    private ChoiceBox<String> customerDropDown;
    @FXML
    private DatePicker startTime;
    @FXML
    private Spinner startTimeSpinner;
    @FXML
    private ComboBox<String> dayNightCombo;
    @FXML
    private Spinner<Integer> durationHours;
    @FXML
    private Spinner<Integer> durationMinutes;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Label errorMessageLabel2;
    private User user;
    private Appointment appointment;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            DataSource datasource = new DataSource();
            datasource.open();
            customerList = datasource.getCustomers();
            System.out.println(customerList);
            datasource.close();
           }
        catch(SQLException e){
        }
        errorMessageLabel.setVisible(false);
        errorMessageLabel2.setVisible(false);
        typeChoiceBox.getItems().addAll("Sales", "Innovation", "Status Update", "Strategy", "Customer Outreach");
        dayNightCombo.getItems().addAll("AM","PM");
        
        for (int i = 0; i < customerList.size(); i++){
            customerDropDown.getItems().add(customerList.get(i).getCustomerName());
        }
        
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,8,0);
        durationHours.setValueFactory(hoursFactory);
        SpinnerValueFactory<Integer> minutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00,59,0);
        durationMinutes.setValueFactory(minutesFactory);

        
        String pattern = "kk:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        SpinnerValueFactory value = new SpinnerValueFactory<LocalTime>() {
        {
        setConverter(new LocalTimeStringConverter(formatter, null));
        }
        @Override
        public void decrement(int steps) {
            if (getValue() == null)
                setValue(appointment.getStart().toLocalTime());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.minusMinutes(steps));
            }
        }
        @Override
        public void increment(int steps) {
            if (this.getValue() == null)
                setValue(appointment.getStart().toLocalTime());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.plusMinutes(steps));
            } 
        } 
    };
        startTimeSpinner.setValueFactory(value);  

}

    @FXML
    private void saveButtonHandler(ActionEvent event) throws SQLException {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String type = typeChoiceBox.getSelectionModel().getSelectedItem();
        String url = URLfield.getText();
        String cust = customerDropDown.getSelectionModel().getSelectedItem();
        LocalDate appointmentDate = startTime.getValue();
        String startTimeValue = startTimeSpinner.getValue().toString();
        String amPm = dayNightCombo.getValue();
        String durationHour = durationHours.getValue().toString();
        String durationMinute = durationMinutes.getValue().toString();
        LocalTime apptStartTime = LocalTime.parse(startTimeValue);
        if(amPm.equalsIgnoreCase("PM")){
            apptStartTime = apptStartTime.plusHours(12);
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
        if((Appointment.validateAppointment(title, description, location, contact, type, url, amPm)) &&
                Appointment.validateAppointmentTime(ldtStart, ltdEnd)){

            DataSource datasource = new DataSource();
            datasource.open();
            datasource.updateAppointment(custId, title, description, location, contact, url, ldtStart, ltdEnd, type, user, appointment);
            datasource.close();
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        else {
            errorMessageLabel.setText("This appointment is either outside business hours");
            errorMessageLabel2.setText("or conflicts with an existing appointment.");
            errorMessageLabel.setStyle("-fx-text-fill: red;");
            errorMessageLabel2.setStyle("-fx-text-fill: red;");
            errorMessageLabel.setVisible(true);
            errorMessageLabel2.setVisible(true);
        }
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void preloadData(User user, Appointment appointment) {
        this.user = user;
        this.appointment = appointment;
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        contactField.setText(appointment.getContact());
        URLfield.setText(appointment.getUrl());
        for (int i = 0; i < customerList.size(); i++){
            if (customerList.get(i).getCustomerID() == appointment.getCustomerId()){
                customerDropDown.getSelectionModel().select(customerList.indexOf(customerList.get(i)));
            }
        }
        typeChoiceBox.getSelectionModel().select(appointment.getType());
        startTime.setValue(appointment.getStart().toLocalDate());
        if(appointment.getStart().getHour() < 12){
            dayNightCombo.getSelectionModel().select("AM");
        }
        else{
            dayNightCombo.getSelectionModel().select("PM");
        }
        startTimeSpinner.getValueFactory().setValue(appointment.getStart().toLocalTime());
        durationHours.getValueFactory().setValue(appointment.getEnd().getHour() - appointment.getStart().getHour());
        durationMinutes.getValueFactory().setValue(appointment.getEnd().getMinute()- appointment.getStart().getMinute());
    }
    
}
