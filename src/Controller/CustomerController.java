/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.DataSource;
import Model.SceneChanger;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CustomerController implements Initializable, ControllerInterface{
    
    @FXML 
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIDcolumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, String> customerAddress2Column;
    @FXML
    private TableColumn<Customer, String> customerCountryColumn;
    @FXML
    private TableColumn<Customer, Integer> customerActiveColumn;
    @FXML
    private Label customerNameLabel, customerEmailLabel;
    @FXML
    private Label customerPhoneLabel, customerAddressLabel;
    @FXML
    private Label customerPostCodeLabel, customerCityLabel;
    @FXML
    private Label customerCountryLabel;
    @FXML
    private CheckBox activeCustomerCheckBox;
    
    @FXML 
    private Button calendarButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private AnchorPane calendarAnchor;
    private User user;
    private Customer customer;
    private Customer detailCustomer;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        activeCustomerCheckBox.setDisable(true);
        customerIDcolumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerAddress2Column.setCellValueFactory(new PropertyValueFactory<>("address2"));
        customerCountryColumn.setCellValueFactory (new PropertyValueFactory<>("country"));
        customerActiveColumn.setCellValueFactory (new PropertyValueFactory<>("active"));
        
        try{
            DataSource datasource = new DataSource();
            datasource.open();
            customerList = datasource.getCustomers();
            System.out.println(customerList);
            datasource.close();
            customerTable.getItems().addAll(customerList);
        }
        catch(SQLException e){
            
        }   
    }
        
        @FXML
        public void rowClicked(MouseEvent event){
             detailCustomer = customerTable.getSelectionModel().getSelectedItem();
             customerNameLabel.setText(detailCustomer.getCustomerName());
             customerEmailLabel.setText(detailCustomer.getEmail());
             customerPhoneLabel.setText(detailCustomer.getCustomerPhone());
             customerAddressLabel.setText(detailCustomer.getAddress());
             customerPostCodeLabel.setText(Integer.toString(detailCustomer.getPostCode()));
             customerCityLabel.setText(detailCustomer.getCity());
             customerCountryLabel.setText(detailCustomer.getCountry());
             if (detailCustomer.getActive() == 1){
                 activeCustomerCheckBox.setSelected(true);
             }
             else {
                 activeCustomerCheckBox.setSelected(false);
             }
             
        } 
        
        @FXML
        private void calendarButtonHandler(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "/Views/Main.fxml", "CalendarOne");
        }
        
        @FXML
        private void createButtonHandler(ActionEvent event) throws IOException{
        SceneChanger sc = new SceneChanger();
        CreateCustomerController ccc = new CreateCustomerController();
        sc.changeScenesNewWindow(event, "/Views/CreateCustomer.fxml", "CalendarOne - Create Customer", user, ccc);
        }
        
        @FXML
        private void editButtonHandler(ActionEvent event) throws IOException{
            SceneChanger sc = new SceneChanger();
            Customer customer = this.customerTable.getSelectionModel().getSelectedItem();
            EditCustomerController ecc = new EditCustomerController();
            sc.changeScenesNewWindow(event, "/Views/EditCustomer.fxml", "CalendarOne - Edit Customer", user, customer, ecc);
        }
        
        @FXML
        public void refreshHandler(ActionEvent event){
            try{
                DataSource datasource = new DataSource();
                datasource.open();
                customerList = datasource.getCustomers();
                System.out.println(customerList);
                datasource.close();
                customerTable.getItems().clear();
                customerTable.getItems().addAll(customerList);
            }
        catch(SQLException e){
            }
        }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }    
}
