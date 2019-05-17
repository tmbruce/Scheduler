/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CalendarTools;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            setCalendarDays();
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCalendarDays() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        String firstDay = CalendarTools.getFirstDayOfMonth(0);
        int dayNum = Calendar.class.getField(firstDay).getInt(this);
        System.out.println(7 - dayNum);
        
        
    }

    
    
    

}
