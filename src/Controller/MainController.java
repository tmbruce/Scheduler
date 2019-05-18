/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CalendarTools;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;



public class MainController implements Initializable {

    
    @FXML
    private Button calendarButton;
    @FXML
    private Label monthLabel;
    @FXML
    private GridPane calendarGrid;
    int daysInWeek = 7;
    int weekRows = 7;
    int totalCalendarDays = 42;
    int monthOffset;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        monthOffset = 0;
        setCalendar(monthOffset);
    }
    
    //This function moves the calendar ahead one month from the current month
    @FXML
    public void nextMonth(ActionEvent event){
        monthOffset++;
        clearCalendar();
        setCalendar(monthOffset);
        calendarButton.requestFocus();
          
    }
    
    //This function moves the calendar back one month from the current month
    @FXML
    public void previousMonth(ActionEvent event){
        monthOffset--;
        clearCalendar();
        setCalendar(monthOffset);
        calendarButton.requestFocus();
    }
    
    /*
    This function clears the calendar all the elements the GridPane above the
    8th index. The first 7 nodes in the index are labels for the calendar days.
    The 8th index is a group for the remaining node elements.
    */
    public void clearCalendar(){
        ObservableList<Node> nodeList = calendarGrid.getChildren();
        nodeList.remove(8, nodeList.size());
    }
    
    /*
    This function initializes the days on the calendar by creating an array
    equal to the number of day spaces in the calendar. It then calculates the
    index of the starting day of the current month and the number of days from 
    preceeding and following months and adds those to the array. A nested loop
    sets as follows: day number -> label -> VBox -> AnchorPane -> GridPane
    */
    public void setCalendar(int monthOffset){
        monthLabel.setText(CalendarTools.getMonth(monthOffset));
        int monthStartDay = CalendarTools.getFirstDayNumber(monthOffset);
        int numberDaysPrevious = CalendarTools.getDaysInMonth(monthOffset + (-1));
        int numberDaysCurrent = CalendarTools.getDaysInMonth(monthOffset);
        int firstCalendarDay = numberDaysPrevious - (monthStartDay - 2);
        ArrayList<Integer> dayList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> dayTest = new ArrayList<ArrayList<Integer>>();
        dayTest.add(new ArrayList<Integer>());
        
        int arrayIndex = 0;
        
        
        for(int i = firstCalendarDay; i < (firstCalendarDay + (monthStartDay - 1)); i++){
            dayList.add(i);
            dayTest.add(arrayIndex, new ArrayList<Integer>(Arrays.asList(i, 0)));
            arrayIndex++;
            
            
        }
        for (int i = 1; i <= numberDaysCurrent; i++){
            dayList.add(i);
            dayTest.add(arrayIndex, new ArrayList<Integer>(Arrays.asList(i, 1)));
            arrayIndex++;
            
            
        }
        int arraySize = dayList.size();
        for (int i = 1; i <= (totalCalendarDays - arraySize); i++){
            dayList.add(i);
            dayTest.add(arrayIndex, new ArrayList<Integer>(Arrays.asList(i, 0)));
            arrayIndex++;
            
        }
        
        System.out.println(dayTest);
        int dayIndex = 0;
        int dayTestIndex = 0;
        //System.out.println(dayTest.get(dayTestIndex).get(0) + ": AT DAYTEST ARRAY LIST 0, 0 - SHOULD BE 28");
        for (int i = 1; i < daysInWeek; i++){
            for(int j = 0; j < weekRows; j++){
                Label dayLabel = new Label();
                dayLabel.setText(dayTest.get(dayTestIndex).get(0).toString());
                if(dayTest.get(dayTestIndex).get(1).equals(0)){
                    dayLabel.getStyleClass().add("inactiveDay");
                }
                else{
                    dayLabel.getStyleClass().add("activeDay");
                }
                //dayLabel.setText(dayList.get(dayIndex).toString());
                VBox dayBox = new VBox();
                dayBox.getChildren().add(dayLabel);
                AnchorPane anchor = new AnchorPane();
                anchor.setTopAnchor(dayBox, 0.0);
                anchor.getChildren().add(dayBox);
                if(dayTest.get(dayTestIndex).get(1).equals(0)){
                    anchor.getStyleClass().add("inactiveDayPane");
                }
                else{
                    anchor.getStyleClass().add("activeDayPane");
                }
                
                calendarGrid.add(anchor, j, i);
                dayIndex++;
                dayTestIndex++;
            }
        }  
    }
    
//    public void setCalendar(int monthOffset){
//        monthLabel.setText(CalendarTools.getMonth(monthOffset));
//        int monthStartDay = CalendarTools.getFirstDayNumber(monthOffset);
//        int numberDaysPrevious = CalendarTools.getDaysInMonth(monthOffset + (-1));
//        int numberDaysCurrent = CalendarTools.getDaysInMonth(monthOffset);
//        int firstCalendarDay = numberDaysPrevious - (monthStartDay - 2);
//        
//        //Adds the final days of the previous month to the calendar
//        for(int i = 0; i < (monthStartDay - 1); i++){
//            
//            Label dayLabel = new Label();
//            dayLabel.setText(""+firstCalendarDay);
//            dayLabel.getStyleClass().add("calendarDay");
//            VBox dayBox = new VBox();
//            dayBox.getChildren().add(dayLabel);
//            AnchorPane anchor = new AnchorPane();
//            anchor.setTopAnchor(dayBox, 0.0);
//            anchor.getChildren().add(dayBox);
//            calendarGrid.add(anchor, i, 1);
//            firstCalendarDay++;
//        }
//        
//        //Adds the remainder of the first week of the present month to the calendar
//        int firstDayOfMonth = 1;
//        int firstDaySecondWeek = 0;
//        for(int i = (CalendarTools.getFirstDayNumber(monthOffset) - 1); i < daysInWeek; i++){
//            Label dayLabel = new Label();
//            dayLabel.setText(""+firstDayOfMonth);
//            dayLabel.getStyleClass().add("calendarDay");
//            VBox dayBox = new VBox();
//            dayBox.getChildren().add(dayLabel);
//            AnchorPane anchor = new AnchorPane();
//            anchor.setTopAnchor(dayBox, 0.0);
//            anchor.getChildren().add(dayBox);                        
//            calendarGrid.add(anchor, i, 1);
//            
//            firstDayOfMonth++;
//            firstDaySecondWeek = firstDayOfMonth;
//        }
//        //firstDaySecondWeek += 1;
//        
//        while(firstDaySecondWeek < numberDaysCurrent) {
//            for(int j = 2; j < weekRows; j++){
//                for(int i = 0; i < daysInWeek; i++){
//                    Label dayLabel = new Label();
//                    dayLabel.setText(""+firstDaySecondWeek);
//                    VBox dayBox = new VBox();
//                    dayBox.getChildren().add(dayLabel);
//                    dayLabel.getStyleClass().add("calendarDay");
//                    AnchorPane anchor = new AnchorPane();
//                    anchor.setTopAnchor(dayBox, 0.0);
//                    anchor.getChildren().add(dayBox);
//                    calendarGrid.add(anchor, i, j);
//                    firstDaySecondWeek++;
//                    if(firstDaySecondWeek > numberDaysCurrent){
//                        break;
//                    }
//                }
//                if(firstDaySecondWeek > numberDaysCurrent){
//                        break;
//                }
//            }
//        }
//        int nextFirstDay = 1;
//        int firstDayNextMonth = (totalCalendarDays - numberDaysCurrent - monthStartDay - 1);
//        int totalDaysOnCalendar = (numberDaysCurrent + (monthStartDay - 1));
//        int columnIndex = totalDaysOnCalendar;
//        columnIndex = (columnIndex - (daysInWeek *(Math.floorDiv(columnIndex, daysInWeek))));
//        int rowIndex = 0;
//        rowIndex = ((int)Math.floorDiv(totalDaysOnCalendar, daysInWeek)) + 1;
//        if (totalDaysOnCalendar < 29){ rowIndex = 4; }
//        if ((totalDaysOnCalendar > 28) && (totalDaysOnCalendar <= 34)){ rowIndex = 5; }
//        if (totalDaysOnCalendar > 35){ rowIndex = 6; }
//        for(int i = columnIndex; i < daysInWeek; i++){
//            //System.out.println(i);
//            Label dayLabel = new Label();
//            dayLabel.setText(""+nextFirstDay);
//            dayLabel.getStyleClass().add("calendarDay");
//            VBox dayBox = new VBox();
//            dayBox.getChildren().add(dayLabel);
//            AnchorPane anchor = new AnchorPane();
//            anchor.setTopAnchor(dayBox, 0.0);
//            anchor.getChildren().add(dayBox);
//            calendarGrid.add(anchor, i, rowIndex);
//            nextFirstDay++;
//        }
//    }
    

        
        
    
    
    
}
            
        


    
    
    


