/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author travi
 */
public class SignInController implements Initializable {
    
    private Boolean loginRegisterClicked;
    @FXML
    private Label registeredOrSignIn;
    @FXML
    private Label userRegisterLabel;
    @FXML
    private Button leftRegisterButton;
    @FXML
    private Label loginUserErrorLabel;
    @FXML
    private Label loginPasswordErrorLabel;
    @FXML
    private Button signInButton;
    @FXML
    private ImageView loginUserIcon;
    @FXML
    private TextField loginUserNameField;
    @FXML
    private ImageView loginPasswordIcon;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private AnchorPane signInPane;
    @FXML
    private AnchorPane backgroundAnchor;
    
    
    private void slide(Node object, int value){
        TranslateTransition transition = new TranslateTransition(Duration.millis(700), object);
        transition.setToX(backgroundAnchor.getLayoutX() + value);
        transition.play();
        
    }
    
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginUserErrorLabel.setVisible(false);
        loginPasswordErrorLabel.setVisible(false);
        loginRegisterClicked = false;
        
        
    }    

    @FXML
    private void registerHandler(MouseEvent event) {
        if (loginRegisterClicked == false) {
            loginUserIcon.setVisible(false);
            loginPasswordIcon.setVisible(false);
            loginUserNameField.setVisible(false);
            loginPasswordField.setVisible(false);
            signInButton.setVisible(false);
            registeredOrSignIn.setVisible(false);
            loginRegisterClicked = true;            
            slide(signInPane, 420);
        }
        else {
            loginUserIcon.setVisible(true);
            loginPasswordIcon.setVisible(true);
            loginUserNameField.setVisible(true);
            loginPasswordField.setVisible(true);
            signInButton.setVisible(true);
            loginRegisterClicked = false;
            slide(signInPane, -420);
        }
        
        
        
        
    }

    @FXML
    private void signInHandler(ActionEvent event) {
    }
    
}
