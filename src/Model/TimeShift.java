
package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;


public class TimeShift {
    
    public LocalDateTime dateTimeBuilder(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }
    
    public LocalDateTime localToUTC(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }
    
    public LocalDateTime UTCtoLocal(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    
    
}
