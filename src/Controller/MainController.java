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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class MainController implements Initializable {

    
    
    @FXML
    private AnchorPane navAnchor;
    private AnchorPane calendarDay;
    private GridPane calendarPane;
    private Label calendarLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           setCalendar();                
        }
    
    private void setCalendar(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                calendarLabel.setText(i + " " + j);
                calendarPane.add(calendarLabel, i, j);
                
            }
        }
    }

}
