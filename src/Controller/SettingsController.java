/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author tbruce
 */
public class SettingsController implements Initializable{

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void settingsButtonHandler(MouseEvent event) {
    }
    
}
