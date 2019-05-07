/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
    private Label forgotUserNameLabel;
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private Label loginPasswordErrorLabel;
    @FXML
    private Button signInButton;
    @FXML
    private TextField loginUserNameField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private AnchorPane signInPane;
    @FXML
    private AnchorPane backgroundAnchor;
    @FXML
    private AnchorPane rightAnchor;
    @FXML
    private AnchorPane leftAnchor;
    
    //Public setter methods to handle locale based language changes
  
    
    private void slide(Node object, int value){
        TranslateTransition transition = new TranslateTransition(Duration.millis(700), object);
        transition.setToX(backgroundAnchor.getLayoutX() + value);
        transition.play();        
    }
    private void fadeOut(Node object, int millis){
        FadeTransition fade = new FadeTransition(Duration.millis(millis), object);
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.play();
    }
    private void fadeIn(Node object){
        FadeTransition fade = new FadeTransition(Duration.millis(300), object);
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setDelay(Duration.millis(500));
        fade.play();
    }
    
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        System.out.println(locale.getLanguage());
        loginUserErrorLabel.setVisible(false);
        loginPasswordErrorLabel.setVisible(false);
        leftAnchor.setVisible(false);
        loginRegisterClicked = false;
    }    

    @FXML
    private void registerHandler(MouseEvent event) {
        if (loginRegisterClicked == false) {
            fadeOut(rightAnchor, 300);
            leftAnchor.setVisible(true);
            fadeOut(leftAnchor, 1);
            fadeIn(leftAnchor);
            loginRegisterClicked = true;            
            slide(signInPane, 420);
            rightAnchor.setVisible(false);
        }
        else {
            rightAnchor.setVisible(true);
            fadeOut(rightAnchor, 1);
            fadeOut(leftAnchor, 300);
            fadeIn(rightAnchor);
            loginRegisterClicked = false;
            slide(signInPane, 0);
        }
    }
    @FXML
    private void registerButtonHandler(ActionEvent event) {
        
    }
        
    @FXML
    private void signInHandler(ActionEvent event) {
        
    }
    @FXML
    private void exitButtonHandler(ActionEvent event) {
        Platform.exit();
    }
    
    //This method translates on screen text to spanish
    public void translateSpanish(){
        loginUserNameField.setPromptText("nombre de usuario");
        loginPasswordField.setPromptText("contraseña");
        forgotPasswordLabel.setText("¿Olvidaste tu nombre de usuario?");
        
    }

    
}
