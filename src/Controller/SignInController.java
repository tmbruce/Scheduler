/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
    import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author travi
 */
public class SignInController implements Initializable {
    
    private Boolean loginRegisterClicked;
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
    
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginUserErrorLabel.setVisible(false);
        loginPasswordErrorLabel.setVisible(false);
        userRegisterLabel.setText("First time user?");
        leftRegisterButton.setText("Register");
        loginRegisterClicked = false;
        
    }    

    @FXML
    private void registerHandler(ActionEvent event) {
        if (loginRegisterClicked == false) {
            loginUserIcon.setVisible(false);
            loginPasswordIcon.setVisible(false);
            loginUserNameField.setVisible(false);
            loginPasswordField.setVisible(false);
            signInButton.setVisible(false);
            userRegisterLabel.setText("Already registered?");
            leftRegisterButton.setText("Sign in");
            loginRegisterClicked = true;
        }
        else {
            loginUserIcon.setVisible(true);
            loginPasswordIcon.setVisible(true);
            loginUserNameField.setVisible(true);
            loginPasswordField.setVisible(true);
            signInButton.setVisible(true);
            userRegisterLabel.setText("First time user?");
            leftRegisterButton.setText("Register");
            loginRegisterClicked = false;
        }
        
        
        
        
    }

    @FXML
    private void signInHandler(ActionEvent event) {
    }
    
}
