/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.SceneChanger;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CustomerController implements Initializable, ControllerInterface{
    
    @FXML private Button calendarButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private AnchorPane calendarAnchor;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
        sc.changeScenes(event, "/Views/CreateCustomer.fxml", "CalendarOne - Create Customer", user, ccc);
        
    }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }
    
}
