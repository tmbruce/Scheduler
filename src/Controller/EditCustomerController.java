
package Controller;

import Model.Customer;
import Model.DataSource;
import Model.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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
    private Button saveButton, cancelButton;
    @FXML
    private CheckBox activeCheckBox;
    private User user;
    private Customer customer;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String address;
    private String address2;
    private String postCode;
    private String city;
    private String country;
    private int active;
    private ArrayList<String> countryList = new ArrayList<>();
    private ArrayList<String> cityList = new ArrayList<>();

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataSource datasource = new DataSource();
        datasource.open();
        countryList = datasource.selectCountries();
        datasource.close();
        customerCityStateCombo.setEditable(false);
    }

    @FXML
    private void comboBoxCountrySelected(ActionEvent event) {
        DataSource datasource = new DataSource();
        datasource.open();
        cityList = datasource.selectCities(customerCountryCombo.getValue());
        datasource.close();
        customerCityStateCombo.getItems().clear();
        customerCityStateCombo.getItems().addAll(cityList);
    }

    @FXML
    private void saveButtonHandler(ActionEvent event) throws SQLException {
        //Get any changes to customer
        saveButton.setDisable(true);
        customerName = customerNameField.getText();
        customerEmail = customerEmailField.getText();
        customerPhone = customerPhoneField.getText();
        address = customerAddressField.getText();
        postCode = (customerPostCodeField.getText());
        city = customerCityStateCombo.getValue();
        country = customerCountryCombo.getValue();
        if(activeCheckBox.isSelected()){
            active = 1;
        }
        else{
            active = 0;
        }
        DataSource datasource = new DataSource();
        datasource.open();
        datasource.updateCustomer(customerName, customerEmail, customerPhone, address, postCode, city, country, active, user, customer);
        datasource.close();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
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
        postCode = Integer.toString(customer.getPostCode());
        customerPostCodeField.setText(String.valueOf(postCode));
        city = customer.getCity();
        customerCityStateCombo.getItems().add(city);
        customerCityStateCombo.getSelectionModel().selectFirst();
        country = customer.getCountry();
        customerCountryCombo.getItems().addAll(countryList);
        int countryIndex = cityList.indexOf(city);
        customerCountryCombo.getSelectionModel().select(country);
        active = customer.getActive();
        if(active == 1) activeCheckBox.setSelected(true);
        this.user = user;
        this.customer = customer;
    }
}
