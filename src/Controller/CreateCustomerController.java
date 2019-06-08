/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.DataSource;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Travis
 */
public class CreateCustomerController implements Initializable, ControllerInterface {

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerPostCodeField;
    @FXML
    private TextField customerEmailField;
    @FXML
    private ComboBox<String> customerCityStateCombo;
    @FXML
    private ComboBox<String> customerCountryCombo;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private ArrayList<String> countryList;
    private ArrayList<String> cityList;
    private User user;
    @FXML
    private CheckBox activeCheckBox;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataSource datasource = new DataSource(); 
        datasource.open();
        countryList = datasource.selectCountries();
        datasource.close();
        customerCountryCombo.getItems().addAll(countryList);
    } 
    
    @FXML
    public void comboBoxCountrySelected(){
        customerCityStateCombo.setEditable(true);
        customerCityStateCombo.getItems().clear();
        DataSource datasource = new DataSource();
        datasource.open();
        cityList = datasource.selectCities(customerCountryCombo.getValue());
        datasource.close();
        customerCityStateCombo.getItems().addAll(cityList);
    }
    
    @FXML
    public void saveButtonHandler(ActionEvent event) throws SQLException, IOException{
        String customerName = customerNameField.getText();
        String customerEmail = customerEmailField.getText();
        String customerPhone = customerPhoneField.getText();
        String customerAddress = customerAddressField.getText();
        String customerPostCode = customerPostCodeField.getText();
        String customerCity = customerCityStateCombo.getValue();
        String customerCountry = customerCountryCombo.getValue();
        
        boolean customerOk = Customer.validateCustomer(customerName, customerEmail, customerPhone, customerAddress, customerPostCode, customerCity, customerCountry);
        if(customerOk == true){
            DataSource datasource = new DataSource();
            datasource.open();
            datasource.insertCustomer(customerName, customerEmail, customerPhone, customerAddress, customerPostCode, customerCity, customerCountry, user);
            datasource.close();
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setContentText("Please complete the form");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }
    
}
