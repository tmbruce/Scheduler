/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DataSource;
import Model.user;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
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
import javafx.scene.input.KeyEvent;
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
    private Label registeredOrSignIn, alreadyRegistered;
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
    private Label registerErrorLabel;
    @FXML
    private Label logoLabel, logoLabel2;
    @FXML
    private Button signInButton, registerButton;
    @FXML
    private TextField loginUserNameField;
    @FXML
    private TextField registerUserName, registerEmail;
    @FXML
    private PasswordField registerPassword1Field, registerPassword2Field;
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
    
  
    //Methods to handle animation
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
    private void fadeOutDelay(Node object, int fadeOutSpeed, int delayTime){
        FadeTransition fade = new FadeTransition(Duration.millis(fadeOutSpeed), object);
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.setDelay(Duration.millis(delayTime));
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
        String language = locale.getLanguage();
        switch(language){
            case "fr":
                translateFrench();
                break;
            case "es":
                translateSpanish();
                break;
        }
        registerErrorLabel.setVisible(false);
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
    private void passwordChecker(KeyEvent Event){
        String password = registerPassword1Field.getText();
        String password2 = registerPassword2Field.getText();
        boolean passCheck = user.validatePassword(password, password2);
        if (passCheck == false){
            registerPassword1Field.setStyle("-fx-text-fill: red;");
            registerPassword2Field.setStyle("-fx-text-fill: red;");
        }
        else {
            registerPassword1Field.setStyle("-fx-text-fill: #00FF00;");
            registerPassword2Field.setStyle("-fx-text-fill: #00FF00;");
        }
    }
    @FXML
    private void registerButtonHandler(ActionEvent event) {
        registerErrorLabel.setVisible(false);
        boolean nameCheck;
        String password = registerPassword1Field.getText();
        String password2 = registerPassword2Field.getText();
        String userName = registerUserName.getText();
        String userEmail = registerEmail.getText();
        boolean passCheck = user.validatePassword(password, password2);
        boolean userCheck = user.validateUserName(userName);
        boolean emailCheck = user.validateEmailAddress(userEmail);
        DataSource datasource = new DataSource();
        datasource.open();
        nameCheck = datasource.verifyExistingUser(userName, userEmail);                                      //DELETE BEFORE SUBMISSION
        datasource.close();
        if((nameCheck == false) && (userCheck == true) && (emailCheck == true)){
            DataSource insert = new DataSource();
            insert.open();
            insert.insertRegistration(userName, userEmail, password);
            insert.close();
            registerPassword1Field.setText("");
            registerPassword2Field.setText("");
            registerUserName.setText("");
            registerEmail.setText("");
        }
        else{
            registerErrorLabel.setText("User name or email already registered");
            registerErrorLabel.setStyle("-fx-text-fill: red;");
            registerErrorLabel.setVisible(true);
            fadeOutDelay(registerErrorLabel, 500, 1800);
            
        }
        
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
        //Sign in
        loginUserNameField.setPromptText("nombre de usuario");                  //username
        loginPasswordField.setPromptText("contraseña");                         //password
        forgotPasswordLabel.setText("¿Olvidaste tu nombre de usuario?");        //Forgot Username?
        forgotUserNameLabel.setText("Se te olvidó tu contraseña");              //Forgot password?
        registeredOrSignIn.setText("¿usuario primerizo? - Registrar aquí.");    //First time user? - Register here
        signInButton.setText("registrarse");                                    //register
        //Logo
        logoLabel.setText("CalendarioUno");                                     //CalendarOne
        logoLabel2.setText("En todos lados. A tiempo.");                        //Everywhere. On time.
        //Register
        registerUserName.setPromptText("nombre de usuario");                    //username
        registerEmail.setPromptText("correo electrónico");                      //email
        registerPassword1Field.setPromptText("contraseña");                     //password
        registerPassword2Field.setPromptText("Escriba la contraseña otra vez"); //re-enter password
        registerButton.setText("registro");                                     //register
        alreadyRegistered.setText("¿Ya registrado? - Firme aquí");              //Already registered? - Sign in here.
    }
    
    //This method translates on screen text to french
    public void translateFrench(){
        //Sign in
        loginUserNameField.setPromptText("Nom d'utilisateur");                  //username
        loginPasswordField.setPromptText("mot de passe");                       //password
        forgotPasswordLabel.setText("identifiant oublié?");                     //Forgot Username?
        forgotUserNameLabel.setText("mot de passe oublié?");                    //Forgot password?
        registeredOrSignIn.setText("Nouvel utilisateur? - Inscrivez-vous ici.");//First time user? - Register here
        signInButton.setText("registre");                                       //register
        //Logo
        logoLabel.setText("PremierCalendrier");                                 //CalendarOne
        logoLabel2.setText("Partout. À temps.");                                //Everywhere. On time.
        //Register
        registerUserName.setPromptText("nom d'utilisateur");                    //username
        registerEmail.setPromptText("email");                                   //email
        registerPassword1Field.setPromptText("mot de passe");                   //password
        registerPassword2Field.setPromptText("retaper le mot de passe");        //re-enter password
        registerButton.setText("registre");                                     //register
        alreadyRegistered.setText("Déjà enregistré? - Se connecter ici");      //Already registered? - Sign in here.
    }
    
    

    
}
