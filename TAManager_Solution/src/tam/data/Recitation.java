/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Suriri
 */
public class Recitation {
    StringProperty section;
    StringProperty instructor;
    StringProperty day;
    StringProperty location;
    StringProperty ta1;
    StringProperty ta2;
    public Recitation(){
        section = new SimpleStringProperty("");
        instructor = new SimpleStringProperty("");
        day = new SimpleStringProperty("");
        location = new SimpleStringProperty("");
        ta1 = new SimpleStringProperty("");
        ta2 = new SimpleStringProperty("");
    }
    
    public Recitation(String newSection, String newInstructor, String newDay,
                      String newLocation, String ta1, String ta2){
        section = new SimpleStringProperty(newSection);
        instructor = new SimpleStringProperty(newInstructor);
        day = new SimpleStringProperty(newDay);
        location = new SimpleStringProperty(newLocation);
        this.ta1 = new SimpleStringProperty(ta1);
        this.ta2 = new SimpleStringProperty(ta2);
    }
    
    public Recitation getRecitation(){
        return this;
    }
    
    public String getSection(){
        return section.get();
    }
    
    public String getInstructor(){
        return instructor.get();
    }
    
    public String getDay(){
        return day.get();
    }
    
    public String getLocation(){
        return location.get();
    }
    
    public String getTa1(){
        return ta1.get();
    }
    
    public String getTa2(){
        return ta2.get();
    }
    
    public void setSection(String newSection){
        section.set(newSection);
    }
    
    public void setInstructor(String newInstructor){
        instructor.set(newInstructor);
    }
    
    public void setDay(String newDay){
        day.set(newDay);
    }
    
    public void setLocation(String newLocation){
        location.set(newLocation);
    }
    
    public void setTa1(String newTA1){
        ta1.set(newTA1);
    }
    
    public void setTa2(String newTA2){
        ta2.set(newTA2);
    }
    
}
