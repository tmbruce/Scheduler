/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CalendarTools;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class MainController implements Initializable {

    
    
    @FXML
    private Label monthLabel;
    @FXML
    private GridPane calendarGrid;
    int daysInWeek = 7;
    int weekRows = 6;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int monthOffset = 0;
        setCalendar(monthOffset);
    }
    
    @FXML
    public void nextMonth(ActionEvent event){
        clearCalendar();
        setCalendar(1);
    }
    
    public void setCalendar(int monthOffset){
        monthLabel.setText(CalendarTools.getMonth(monthOffset));
        int monthStartDay = CalendarTools.getFirstDayNumber(monthOffset);
        int numberDaysPrevious = CalendarTools.getDaysInMonth(monthOffset + (-1));
        int numberDaysCurrent = CalendarTools.getDaysInMonth(monthOffset);
        int firstCalendarDay = numberDaysPrevious - (monthStartDay - 2);
        
        //Adds the final days of the previous month to the calendar
        for(int i = 0; i < (monthStartDay - 1); i++){
            Label dayLabel = new Label();
            dayLabel.setText(""+firstCalendarDay);
            VBox dayBox = new VBox();
            dayBox.getChildren().add(dayLabel);
            calendarGrid.add(dayLabel, i, 1);
            firstCalendarDay++;
        }
        
        //Adds the remainder of the first week of the present month to the calendar
        int firstDayOfMonth = 1;
        int firstDaySecondWeek = 0;
        for(int i = (CalendarTools.getFirstDayNumber(monthOffset) - 1); i < daysInWeek; i++){
            Label dayLabel = new Label();
            dayLabel.setText(""+firstDayOfMonth);
            VBox dayBox = new VBox();
            dayBox.getChildren().add(dayLabel);
            calendarGrid.add(dayLabel, i, 1);
            firstDayOfMonth++;
            firstDaySecondWeek = firstDayOfMonth;
        }
        //firstDaySecondWeek += 1;
        
        do {
            for(int j = 2; j < weekRows; j++){
                for(int i = 0; i < daysInWeek; i++){
                    Label dayLabel = new Label();
                    dayLabel.setText(""+firstDaySecondWeek);
                    VBox dayBox = new VBox();
                    dayBox.getChildren().add(dayLabel);
                    calendarGrid.add(dayLabel, i, j);
                    firstDaySecondWeek++;
                }
            }
        } while (firstDaySecondWeek < (numberDaysCurrent - 2));
        
    }
    
    public void clearCalendar(){
            ObservableList<Node> children = calendarGrid.getChildren();
            children.clear();
        }
}

    
    
    


