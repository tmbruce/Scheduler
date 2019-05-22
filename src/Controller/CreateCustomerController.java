/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DataSource;
import Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private ComboBox<String> customerCityStateCombo;
    @FXML
    private ComboBox<String> customerCountryCombo;
    private ObservableList<String> countryList = FXCollections.observableArrayList();
    private ObservableList<String> cityList = FXCollections.observableArrayList();
    private User user;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataSource datasource = new DataSource(); 
        datasource.open();
        countryList = (ObservableList<String>) datasource.selectCountries();
        datasource.close();
        customerCountryCombo.getItems().addAll(countryList);
    } 
    
    @FXML
    public void comboBoxCountrySelected(){
        DataSource datasource = new DataSource();
        datasource.open();
        cityList = (ObservableList<String>) datasource.selectCities(customerCountryCombo.getValue().toString());
        datasource.close();
        customerCityStateCombo.getItems().addAll(cityList);
    }
    

    
    
    
    
    
    @FXML
    private void saveButtonHandler(ActionEvent event) {
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
    }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }
    
}
