/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author travi
 */
public class EditCustomerController implements Initializable, ControllerInterfaceUser{

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerEmailField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerPostCodeField;
    @FXML
    private ComboBox<?> customerCityStateCombo;
    @FXML
    private ComboBox<?> customerCountryCombo;
    @FXML
    private Button saveButton;
    @FXML
    private CheckBox activeCheckBox;
    private User user;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        }
    
    @FXML
    private void comboBoxCountrySelected(ActionEvent event) {
    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
    }


    @Override
    public void preloadData(User user, Customer customer) {
        }


    
}
