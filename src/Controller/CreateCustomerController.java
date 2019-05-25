/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DataSource;
import Model.SceneChanger;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

        DataSource datasource = new DataSource();
        datasource.open();
        datasource.insertCustomer(customerName, customerEmail, customerPhone, customerAddress, customerPostCode, customerCity, customerCountry, user);
        datasource.close();
//        CustomerController cc = new CustomerController();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
//        CreateCustomerController ccc = new CreateCustomerController();
//        SceneChanger sc = new SceneChanger();
//        sc.changeScenes(event, "/Views/Customers.fxml", "CalendarOne - Manage Customers", user, ccc);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
    }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }
    
}
