
package Controller;

import Model.Appointment;
import Model.DataSource;
import Model.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
