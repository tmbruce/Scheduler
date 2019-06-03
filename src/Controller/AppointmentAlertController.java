/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.DataSource;
import Model.User;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author travi
 */
public class AppointmentAlertController implements Initializable, ControllerInterfaceApp {

    /**
     * Initializes the controller class.
     */
    private User user;
    private Appointment appointment;
    @FXML
    private Button okButton;
    @FXML
    private Label messageLabel;
    private String customerName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataSource datasource = new DataSource();
        datasource.open();
        try {
            customerName = datasource.getCustomerFromID(appointment.getUserId());
        } 
        catch (SQLException ex) {
        }
        datasource.close();
    }    
    
    @FXML
    public void okButtonHandler(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void preloadData(User user, Appointment appointment) {
        this.user = user;
        this.appointment = appointment;
        
    }
}
