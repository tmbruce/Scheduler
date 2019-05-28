
package Controller;

import Model.CalendarTools;
import Model.DataSource;
import Model.SceneChanger;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable, ControllerInterface {
    
    @FXML
    private Button calendarButton, customersButton;
    @FXML
    private Label monthLabel;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private Button settingsButton;
    @FXML
    private Button reportsButton;
    @FXML
    private AnchorPane calendarAnchor;
    private final int daysInWeek = 7;
    private final int weekRows = 7;
    private final int totalCalendarDays = 42;
    private int monthOffset;
    private User user;
    private String currentUserName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        monthOffset = 0;
        setCalendar(monthOffset);
    }

    
    @FXML
    public void createAppointmentHandler(ActionEvent event) throws IOException{
        SceneChanger sc = new SceneChanger();
        CreateAppointmentController cac = new CreateAppointmentController();
        sc.changeScenesNewWindow(event, "/Views/CreateAppointment.fxml", "CalendarOne - Create Appointment", user, cac);
    }
    
    @FXML
    public void customersButtonHandler(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        CustomerController customerController = new CustomerController();
        sc.changeScenes(event, "/Views/Customers.fxml", "CalendarOne - Customers", user, customerController);  
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
    
    //Allows for other controllers to access the current offset month of the calendar.
    public int getCurrentOffset(){
        return monthOffset;
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
        ArrayList<ArrayList<Integer>> dayList = new ArrayList<>();
        //dayList.add(new ArrayList<>());
        int arrayIndex = 0;

        //Get the days of the preceeding month calendar
        for(int i = firstCalendarDay; i < (firstCalendarDay + (monthStartDay - 1)); i++){
            dayList.add(arrayIndex, new ArrayList<>(Arrays.asList(i, 0)));
            arrayIndex++;   
        }
        
        //Get the days of the current month
        for (int i = 1; i <= numberDaysCurrent; i++){
            dayList.add(arrayIndex, new ArrayList<>(Arrays.asList(i, 1)));
            arrayIndex++;  
        }
        
        //Get the days of the next month to fill out the remaining days on the calendar
        int arraySize = dayList.size();
        for (int i = 1; i <= (totalCalendarDays - arraySize + 1); i++){
            dayList.add(arrayIndex, new ArrayList<>(Arrays.asList(i, 0)));
            arrayIndex++;
        }
        
        //Set all days on the calendar along with styling to indicate if the day is
        //a day in the current month, a day in a previous or upcoming month, or the current day
        int dayIndex = 0;
        for (int i = 1; i < daysInWeek; i++){
            for(int j = 0; j < weekRows; j++){
                Label dayLabel = new Label();
                dayLabel.setText(dayList.get(dayIndex).get(0).toString());
                if(dayList.get(dayIndex).get(1).equals(0)){
                    dayLabel.getStyleClass().add("inactiveDay");
                }
                else{
                    dayLabel.getStyleClass().add("activeDay");
                }

                VBox dayBox = new VBox();
                dayBox.getChildren().add(dayLabel);
                AnchorPane anchor = new AnchorPane();
                anchor.setTopAnchor(dayBox, 0.0);
                anchor.getChildren().add(dayBox);
                if(dayList.get(dayIndex).get(0).equals(CalendarTools.getDate()) &&
                   monthOffset == 0){
                    anchor.getStyleClass().add("today");
                }
                else if (dayList.get(dayIndex).get(1).equals(0)){
                    anchor.getStyleClass().add("inactiveDayPane");
                }
                else{
                    anchor.getStyleClass().add("activeDayPane");
                }
                
                calendarGrid.add(anchor, j, i);
                dayIndex++;
            }
        }  
    }    

    @Override
    public void preloadData(User user) {
        this.user = user;
        currentUserName = user.getUserName();
        if(currentUserName.contains("@")){
            DataSource datasource = new DataSource();
            datasource.open();
            String newUserName = datasource.getUserNameFromEmail(currentUserName);
            datasource.close();
            user.setUserName(newUserName);
        }
    }
}