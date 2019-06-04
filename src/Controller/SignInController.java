/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DataSource;
import Model.PassEncrypt;
import Model.SceneChanger;
import Model.User;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
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
    private Label forgotUserNameLabel;
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private Label registerErrorLabel;
    @FXML
    private Label logoLabel, logoLabel2;
    @FXML
    private Label registerMessage, registerMessage2;
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
    private boolean inserted;
    private boolean signInClicked;
    
    //NOTE TO EVALUATOR - TO CHANGE LANGUAGE TO FRENCH OR SPANISH, COMMENT OUT line language = getLanguage in the initialize method
    //and uncomment either "es" or "fr". This will change the display language as well as error messages.
    public String language;
    //public String language = "es";
    //public String language = "fr";    
  
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
    //Make language publiclly available
    public static String getLanguage(){
        return Locale.getDefault().getLanguage();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        language = getLanguage();
        switch(language){
            case "fr":
                translateFrench();
                break;
            case "es":
                translateSpanish();
                break;
        }
        inserted = false;
        registerErrorLabel.setVisible(false);
        leftAnchor.setVisible(false);
        loginRegisterClicked = false;
        signInClicked = false;
        registerMessage.setVisible(false);
        registerMessage2.setVisible(false);
    }

    @FXML
    private void hideRegisterLabels(KeyEvent event){
        registerMessage.setVisible(false);
        registerMessage2.setVisible(false);
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
        boolean passCheck = User.validatePassword(password, password2);
        if (passCheck == false){
            registerPassword1Field.setStyle("-fx-text-fill: red;");
            registerPassword2Field.setStyle("-fx-text-fill: red;");
        }
        else {
            registerPassword1Field.setStyle("-fx-text-fill: #4CBB17;");
            registerPassword2Field.setStyle("-fx-text-fill: #4CBB17;");
        }
    }
    @FXML
    private void registerButtonHandler(ActionEvent event) {
        if (inserted == false){
            registerErrorLabel.setVisible(false);
            boolean nameCheck;
            //Grab user entered text
            String password = registerPassword1Field.getText();
            String password2 = registerPassword2Field.getText();
            String userName = registerUserName.getText();
            String userEmail = registerEmail.getText();
            //Simple validation for user entered fields
            boolean passCheck = User.validatePassword(password, password2);
            boolean userCheck = User.validateUserName(userName);
            boolean emailCheck = User.validateEmailAddress(userEmail);
            //Encrypts password
            String hashedPassword = PassEncrypt.encryptPassword(password);
            //Initiate database connection
            DataSource datasource = new DataSource();
            datasource.open();
            //Check to see if user or email exists in the database
            nameCheck = datasource.verifyExistingUser(userName, userEmail);
            datasource.close();
            if ((passCheck == true) && (emailCheck == true)){
                if(nameCheck == false){
                //Insert user into database and clear entry fields.

                DataSource insert = new DataSource();
                insert.open();
                insert.insertRegistration(userName, userEmail, hashedPassword);
                insert.close();
                inserted = true;
                registerPassword1Field.setText("");
                registerPassword2Field.setText("");
                registerUserName.setText("");
                registerEmail.setText("");
                rightAnchor.setVisible(true);
                fadeOut(rightAnchor, 1);
                fadeOut(leftAnchor, 300);
                fadeIn(rightAnchor);
                registerMessage.setVisible(true);
                registerMessage2.setVisible(true);
                fadeOut(registerMessage, 1);
                fadeOut(registerMessage2, 1);
                fadeIn(registerMessage);
                fadeIn(registerMessage2);
                loginRegisterClicked = false;
                slide(signInPane, 0);
            }

                else{
                    switch(language){
                        case "fr":
                            registerErrorLabel.setText("Nom d'utilisateur ou courriel déjà enregistré");
                            break;
                        case "es":
                            registerErrorLabel.setText("Nombre de usuario o correo electrónico ya registrado");
                            break;
                        default:
                            registerErrorLabel.setText("User name or email already registered");
                            break;
                    }
                registerErrorLabel.setStyle("-fx-text-fill: red;");
                registerErrorLabel.setVisible(true);
                fadeOutDelay(registerErrorLabel, 500, 1800);
                }
            }
            else {
                switch(language){
                    case "fr":
                        registerErrorLabel.setText("Erreur dans le mot de passe ou l'adresse email");
                        break;
                    case "es":
                        registerErrorLabel.setText("Error en la contraseña o dirección de correo electrónico");
                        break;
                    default:
                        registerErrorLabel.setText("Error in password or email address");
                }
                registerErrorLabel.setStyle("-fx-text-fill: red;");
                registerErrorLabel.setVisible(true);
                fadeOutDelay(registerErrorLabel, 500, 2500);
            }
        }
    }
        
        
    @FXML
    private void signInHandler(ActionEvent event) throws IOException {
        if(signInClicked == false){
            String userName = loginUserNameField.getText();
            String password = loginPasswordField.getText();
            String encryptedPass = PassEncrypt.encryptPassword(password);
            if(userName.contains("@")){
                boolean success = false;
                DataSource loginWithEmail = new DataSource();
                loginWithEmail.open();
                success = loginWithEmail.loginWithEmail(userName, encryptedPass);
                loginWithEmail.close();
                if(success == true){
                    //Logs user information to text file
                    String file = "accessLog.txt";
                    PrintWriter pw = new PrintWriter(new FileWriter(file, true));
                    pw.append("\nUser " + userName + " accessed the system at " + LocalDateTime.now(ZoneId.of("UTC")));
                    pw.close();
                    //Creates user object to pass through as active user
                    User user = new User(userName);
                    SceneChanger sc = new SceneChanger();
                    MainController mainController  = new MainController();
                    sc.changeScenes(event, "/Views/Main.fxml", "CalendarOne", user, mainController);
                }
                else{
                    registerMessage2.setVisible(true);
                    fadeOut(registerMessage, 1);
                    language = getLanguage();
                    switch(language){
                        case "fr":
                            registerMessage2.setText("Le nom d'utilisateur ou le mot de passe ne correspond pas à nos enregistrements");
                            break;
                        case "es":
                            registerMessage2.setText("Nombre de usuario o contraseña no coincide con nuestros registros");
                            break;
                        default:
                            registerMessage2.setText("Username or password does not match our records");
                    }
                    registerMessage2.setStyle("-fx-text-fill: red;");
                    fadeIn(registerMessage2);
                }
            }
            else{
                boolean success = false;
                DataSource loginWithUserName = new DataSource();
                loginWithUserName.open();
                success = loginWithUserName.loginWithUserName(userName, encryptedPass);
                loginWithUserName.close();
                if(success == true){
                    //Logs user information to text file
                    String file = "accessLog.txt";
                    PrintWriter pw = new PrintWriter(new FileWriter(file, true));
                    pw.append("\nUser " + userName + " accessed the system at " + LocalDateTime.now(ZoneId.of("UTC")));
                    pw.close();
                    //Creates user to pass through as active user
                    User user = new User(userName);
                    SceneChanger sc = new SceneChanger();
                    MainController mainController  = new MainController();
                    sc.changeScenes(event, "/Views/Main.fxml", "CalendarOne", user, mainController);
                }
                else{
                    registerMessage2.setVisible(true);
                    fadeOut(registerMessage, 1);
                    language = getLanguage();
                    switch(language){
                        case "fr":
                            registerMessage2.setText("Le nom d'utilisateur ou le mot de passe ne correspond pas à nos enregistrements");
                            break;
                        case "es":
                            registerMessage2.setText("Nombre de usuario o contraseña no coincide con nuestros registros");
                            break;
                        default:
                            registerMessage2.setText("Username or password does not match our records");
                    }
                    registerMessage2.setStyle("-fx-text-fill: red;");
                    fadeIn(registerMessage2);
                }
            }
        }
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
