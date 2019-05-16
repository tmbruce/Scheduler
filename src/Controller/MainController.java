/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CalendarTools;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;



public class MainController implements Initializable {

    
    
    @FXML
    private Label monthLabel;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthLabel.setText(CalendarTools.getMonth(0));
        System.out.println(CalendarTools.getFirstDayOfMonth(0));
    }
    
    public void setCalendarDays(){
        String firstDay = CalendarTools.getFirstDayOfMonth(0);
    }

    
    
    

}
