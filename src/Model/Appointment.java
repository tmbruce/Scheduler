
package Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public Appointment(int customerId, int userId, String title, String description, String location, String contact, String type, String url, LocalDateTime start) {
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
    }
    public ObservableList getAppointments(){
        return appointmentList;
    }
    
    public void setAppointment(Appointment appointment){
        appointmentList.add(appointment);
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    } 
    
    //Validation methods
    public boolean validateAppointment(String title, String description, String location, String contact, String type, String url){
        if((title != null) && (description != null) && (location != null) && (contact != null) && (type != null) && (url != null)){  
        }
        return true;
    }
    
    public boolean validateAppointmentTime(LocalDateTime start, LocalDateTime end){
        boolean apptAvailable = true;
        LocalTime startOfDay = LocalTime.parse("07:00");
        LocalTime endOfDay = LocalTime.parse("18.00");
        for (int i = 0; i < appointmentList.size(); i++){
            LocalDateTime apptStart = appointmentList.get(i).getStart();
            LocalDateTime apptEnd = appointmentList.get(i).getEnd();
            if ((start.isAfter(apptStart)) && (start.isBefore(apptEnd))){
                apptAvailable = false;
            }
            if ((end.isAfter(apptStart)) && (end.isBefore(apptEnd))){
                apptAvailable = false;
            }
            if ((start.getHour() >= endOfDay.getHour()) || start.getHour() <= startOfDay.getHour()){
                apptAvailable = false;
            }
            if((end.getHour() >= endOfDay.getHour() || end.getHour() <= startOfDay.getHour())){
                apptAvailable = false;
            }
            if ((start.getDayOfWeek().toString().equals("SATURDAY")) || (start.getDayOfWeek().toString().equals("SUNDAY"))){
                apptAvailable = false;
            }
            if ((end.getDayOfWeek().toString().equals("SATURDAY")) || (end.getDayOfWeek().toString().equals("SUNDAY"))){
                apptAvailable = false;
            }
        }
        return apptAvailable;
    }
}

