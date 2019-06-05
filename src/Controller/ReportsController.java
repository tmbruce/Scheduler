/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.SceneChanger;
import Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.User;
import java.io.IOException;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author travi
 */
public class ReportsController implements Initializable, ControllerInterface {

    @FXML
    private Button calendarButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    private User user;
    @FXML
    private AnchorPane calendarAnchor;
    @FXML
    private ChoiceBox<?> typeChoiceBox;
    @FXML
    private ChoiceBox<?> monthChoiceBox;
    @FXML
    private Label numberOfApptsLabel;
    @FXML
    private TableView<?> scheduleTableView;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private TableColumn<?, ?> timeColumn;
    @FXML
    private TableColumn<?, ?> customerColumn;
    @FXML
    private TableColumn<?, ?> typeColumn;
    @FXML
    private TableColumn<?, ?> locationColumn;
    @FXML
    private TableColumn<?, ?> contactColumn;
    @FXML
    private ChoiceBox<?> userChoiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       reportsButton.requestFocus();
    }    

    @FXML
    public void customersButtonHandler(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        CustomerController customerController = new CustomerController();
        sc.changeScenes(event, "/Views/Customers.fxml", "CalendarOne - Customers", user, customerController);  
        customersButton.requestFocus();
    }    

    @FXML
    private void calendarButtonHandler(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        MainController mc = new MainController();
        sc.changeScenes(event, "/Views/Main.fxml", "CalendarOne", user, mc);
    }

    @Override
    public void preloadData(User user) {
        this.user = user;
    }
}
