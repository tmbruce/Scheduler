/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 *
 * @author travi
 */
public class CalendarTools {
    public static String getMonth(int monthOffset){
        String calendarMonth = null;
        Calendar month = getCalendar(monthOffset);
        int monthNum = month.get(java.util.Calendar.MONTH);
        switch (monthNum){
        case 0:
            calendarMonth = "January";
            break;
        case 1:
            calendarMonth = "February";
            break;
        case 2:
            calendarMonth = "March";
            break;
        case 3:
            calendarMonth = "April";
            break;
        case 4:
            calendarMonth = "May";
            break;
        case 5:
            calendarMonth = "June";
            break;
        case 6:
            calendarMonth = "July";
            break;
        case 7:
            calendarMonth = "August";
            break;
        case 8:
            calendarMonth = "September";
            break;
        case 9:
            calendarMonth = "October";
            break;
        case 10:
            calendarMonth = "November";
            break;
        case 11:
            calendarMonth = "December";
            break;
                    }
     return calendarMonth;
    }
    
    public static String getFirstDayOfMonth(int monthOffset){
        String day = null;
        Calendar calendar = getCalendar(monthOffset);
        YearMonth firstDay = YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        return day = firstDay.atDay(1).getDayOfWeek().name();
    }
    
    private static Calendar getCalendar(int monthOffset){
       int offset = TimeZone.getDefault().getRawOffset();
       String localZone = TimeZone.getDefault().getID();
       SimpleTimeZone local = new SimpleTimeZone(offset, localZone);
       local.setStartRule(java.util.Calendar.MARCH, 2, java.util.Calendar.SUNDAY, java.util.Calendar.DST_OFFSET);
       local.setEndRule(java.util.Calendar.OCTOBER, 1, java.util.Calendar.SUNDAY, java.util.Calendar.DST_OFFSET);
       java.util.Calendar calendar = new GregorianCalendar(local);
       Date date = new Date();
       calendar.setTime(date);
       calendar.add(Calendar.MONTH, monthOffset);
       
       return calendar;
       
    }
}
