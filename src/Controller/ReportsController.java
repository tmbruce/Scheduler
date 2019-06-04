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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void customersButtonHandler(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        MainController mc = new MainController();
        sc.changeScenes(event, "/Views/Main.fxml", "CalendarOne", user, mc);
    }    

    @Override
    public void preloadData(User user) {
        this.user = user;
    }
}
