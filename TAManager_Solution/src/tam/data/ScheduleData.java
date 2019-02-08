/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tam.TAManagerApp;
import tam.workspace.TAWorkspace;
import java.util.Date;

/**
 *
 * @author Suriri
 */
public class ScheduleData {
    TAManagerApp app;
    ObservableList<Schedule> schedule;
    String startDate;
    String endDate;
    
    public ScheduleData(TAManagerApp initApp){
        app = initApp;
        schedule = FXCollections.observableArrayList();
        startDate = "0/0/0000";
        endDate = "1/1/1111";
    }
    
    public ScheduleData(){
        schedule = FXCollections.observableArrayList();
        startDate = "0/0/0000";
        endDate = "1/1/1111";
    }
    
    public String getStartDate(){
        return startDate;
    }
    
    public String getEndDate(){
        return endDate;
    }
    
    public ObservableList<Schedule> getSchedule(){
        return schedule;
    }
    
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
    
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }
    
    public int[] getTime(String date){
        String[] dateList = date.split("/");
        int[] timeInt = new int[3];
        for(int i = 0; i < timeInt.length; i++){
            timeInt[i] = Integer.parseInt(dateList[i]);
        }
        return timeInt;
    }
    
    public boolean isMonday(LocalDate localDate){
        Date date = java.sql.Date.valueOf(localDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean monday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
        return monday;
    }
    
    public boolean isFriday(LocalDate localDate){
        Date date = java.sql.Date.valueOf(localDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean friday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
        return friday;
    }
    
    public boolean isCorrectDate(int[] startDate, int[] endDate){
        if (startDate[2] <= endDate[2]){
            if (startDate[0] <= endDate[0]){
                if (startDate[0] == endDate[0] && (startDate[1] > endDate[1])){
                    return false;
                }
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isCorrectDate(String[] startDate, String[] endDate){
        if (Integer.parseInt(startDate[2]) <= Integer.parseInt(endDate[2])){
            if (Integer.parseInt(startDate[0]) <= Integer.parseInt(endDate[0])){
                if (Integer.parseInt(startDate[0]) == Integer.parseInt(endDate[0])
                        && (Integer.parseInt(startDate[1]) > Integer.parseInt(endDate[1]))){
                    return false;
                }
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isCorrectDateV1(String[] startDate, String[] endDate){
        if (Integer.parseInt(startDate[2]) <= Integer.parseInt(endDate[2])){
            if (Integer.parseInt(startDate[0]) <= Integer.parseInt(endDate[0])){
                if (Integer.parseInt(startDate[0]) == Integer.parseInt(endDate[0])
                        && Integer.parseInt(startDate[1]) > Integer.parseInt(endDate[1])){
                    return false;
                }
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isCorrectDateV2(String[] startDate, String[] endDate){
        if (Integer.parseInt(startDate[0]) <= Integer.parseInt(endDate[0])){
            if (Integer.parseInt(startDate[1]) <= Integer.parseInt(endDate[1])){
                if (Integer.parseInt(startDate[1]) == Integer.parseInt(endDate[1])
                        && Integer.parseInt(startDate[2]) > Integer.parseInt(endDate[2])){
                    return false;
                }
                return true;
            }
        }
        
        return false;
    }
    
    public Schedule getSchedule(String type, String date, String time, String title, String topic, String link,
            String criteria){
        for (int i = 0; i < schedule.size(); i++){
            Schedule sche = schedule.get(i);
            if (sche.getType().equals(type) && sche.getDate().equals(date) &&
                    sche.getTime().equals(time) && sche.getTitle().equals(title) &&
                    sche.getTopic().equals(topic) && sche.getLink().equals(link) &&
                    sche.getCriteria().equals(criteria)){
                return sche;
            }
        }
        return null;
    }
    
    
    public void initDate(String start, String end){
        startDate = start;
        endDate = end;
        
        if(app == null){
            
        }
        else{
            TAWorkspace workspaceComponent = (TAWorkspace) app.getWorkspaceComponent();
            
            String[] s = startDate.split("/");
            int[] st = new int[3];
            
            for (int i = 0; i < 3; i++){
                st[i] = Integer.parseInt(s[i]);
            }
            
            workspaceComponent.getScheduleStart().setValue(LocalDate.of(st[2], st[0], st[1]));
            
            String[] e = endDate.split("/");
            int[] et = new int[3];
            
            for (int i = 0; i < 3; i++){
                et[i] = Integer.parseInt(e[i]);
            }
            
            workspaceComponent.getScheduleEnd().setValue(LocalDate.of(et[2], et[0], et[1]));
            
            
            //DateTimeFormatter formatter;
            //formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            //LocalDate startD;
            //startD = LocalDate.parse(startDate, formatter);
            //LocalDate endD;
            //endD = LocalDate.parse(endDate, formatter);
            
            //workspaceComponent.getScheduleStart().setValue(startD);
            //workspaceComponent.getScheduleEnd().setValue(endD);
            //workspaceComponent.getOfficeHour(false).getSelectionModel().select(endDate);
        }
    }
    
    public void addSchedule(String newType, String newDate, String Time, String Title, String Topic, String link,
            String criteria){
        //int[] startDateInt = getTime(startDate);
        //int[] endDateInt = getTime(endDate);
        //int[] currentDateInt = getTime(newDate);
        //if (currentDateInt[2] >= startDateInt[2] && currentDateInt[2] <= endDateInt[2]){
          //  if (currentDateInt[0] >= startDateInt[0] && currentDateInt[0] <= endDateInt[0]){
            //    if (currentDateInt[1] >= startDateInt[1] && currentDateInt[1] <= endDateInt[1]){
                    Schedule sche = new Schedule(newType, newDate, Time, Title, Topic, link, criteria);
                    schedule.add(sche);
              //  }
            //}
        //}
        
            
    }
    
    public void removeSchedule(String type, String date, String time, String title,
            String topic, String link, String criteria){
        for (int i = 0; i < schedule.size(); i++){
            Schedule sche = schedule.get(i);
            if (sche.getType().equals(type) && sche.getDate().equals(date) &&
                    sche.getTime().equals(time) && sche.getTitle().equals(title) &&
                    sche.getTopic().equals(topic) && sche.getLink().equals(link) &&
                    sche.getCriteria().equals(criteria)){
                schedule.remove(sche);
            }
        }
    }
    
    public void resetData(){
        startDate = "0/0/0000";
        endDate = "1/1/1111";
        schedule.clear();
        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getScheduleTypeCombo().getSelectionModel().select(null);
        workspaceComponent.getScheduleStart().setValue(null);
        workspaceComponent.getScheduleEnd().setValue(null);
        workspaceComponent.getScheduleDate().setValue(null);
        workspaceComponent.getScheduleStart().getEditor().clear();
        workspaceComponent.getScheduleEnd().getEditor().clear();
        workspaceComponent.getScheduleDate().getEditor().clear();
    }
    
    public boolean isContains(String type, String date, String time, String title,
            String topic, String link, String criteria){
        for (Schedule sche : schedule){
            if (sche.getType().equals(type) && sche.getDate().equals(date) &&
                    sche.getTime().equals(time) && sche.getTitle().equals(title) &&
                    sche.getTopic().equals(topic) && sche.getLink().equals(link) &&
                    sche.getCriteria().equals(criteria)){
                return true;
            }
        }
        return false;
    }
    
}
