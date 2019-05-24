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
import java.util.ArrayList;
import java.util.Arrays;
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
    private ComboBox<String> customerCityStateCombo;
    @FXML
    private ComboBox<String> customerCountryCombo;
    @FXML
    private Button saveButton;
    @FXML
    private CheckBox activeCheckBox;
    private User user;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String address;
    private String address2;
    private int postCode;
    private String city;
    private String country;
    private int active;
    private ArrayList<String> cities;
    private ArrayList<String> countries;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataSource datasource = new DataSource();
        datasource.open();        
        countries = datasource.getLocations();
        datasource.close();
        System.out.println(countries.get(0).get(1));
//        for(int i = 0; i < countries.size(); i++){
//            System.out.println(countries.);
//        }
        
        
        
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
        customerName = customer.getCustomerName();
        customerNameField.setText(customerName);
        customerEmail = customer.getEmail();
        customerEmailField.setText(customerEmail);
        customerPhone = customer.getCustomerPhone();
        customerPhoneField.setText(customerPhone);
        address = customer.getAddress();
        customerAddressField.setText(address);
        address2 = customer.getAddress2();
        postCode = customer.getPostCode();
        customerPostCodeField.setText(String.valueOf(postCode));
        city = customer.getCity();
        country = customer.getCountry();
//        for (int i = 0; i < countries.size(); i++){
//            System.out.println(countries.get(1));
//            if(countries.get(1).equals(city)){
//                customerCountryCombo.getItems().add(countries.get(0));
//            }
//        }
        active = customer.getActive();
        if(active == 1) activeCheckBox.setSelected(true);
        


    }
}
