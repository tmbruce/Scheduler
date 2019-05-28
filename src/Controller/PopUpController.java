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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class PopUpController implements Initializable, ControllerInterfaceUser {

    private User user;
    private Customer customer;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label headerLabel;
    @FXML
    private Label messageLabel, messageLabel2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        headerLabel.setText("Alert!");
        messageLabel.setText("Customers deleted from the database cannot be recovered.");
        messageLabel2.setText("Continue?");
        
    }    

    @FXML
    private void okButtonHandler(ActionEvent event) throws SQLException {
        DataSource datasource = new DataSource();
        datasource.open();
        datasource.deleteCustomer(customer, user);
        datasource.close();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();  
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void preloadData(User user, Customer customer) {
        this.user = user;
        this.customer = customer;
    }
    
}
