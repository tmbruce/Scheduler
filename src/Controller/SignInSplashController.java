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
import javafx.stage.Stage;



public class SignInSplashController implements Initializable {

@FXML
private Button signInButton;
@FXML
private Label logoLabel;
@FXML
private Label logoLabel2;
@FXML
private Label registerMessage;
@FXML
private Label registerMessage2;

private String language;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        language = SignInController.getLanguage();
        switch(language){
        case "fr":
            translateFrench();
            break;
        case "es":
            translateSpanish();
            break;
        }
    }
    
    private void translateFrench(){
        signInButton.setText("registre");
        logoLabel.setText("PremierCalendrier");
        logoLabel2.setText("Partout. À temps.");
        registerMessage.setText("Succès d'inscription!");
        registerMessage2.setText("Connectez-vous avec vos nouveaux identifiants."); 
    }
    private void translateSpanish(){
        signInButton.setText("registrarse");
        logoLabel.setText("CalendarioUno");
        logoLabel2.setText("En todos lados. A tiempo.");
        registerMessage.setText("¡Registración exitosa!");
        registerMessage2.setText("Inicia sesión con tus nuevas credenciales.");
    }
    
    public void signInHandler(ActionEvent event){
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.close();
    }
    
}
