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
public class Schedule {
    StringProperty type;
    StringProperty date;
    StringProperty time;
    StringProperty title;
    StringProperty topic;
    StringProperty link;
    StringProperty criteria;
    public Schedule(){
        type = new SimpleStringProperty("");
        date = new SimpleStringProperty("");
        time = new SimpleStringProperty("");
        title = new SimpleStringProperty("");
        topic = new SimpleStringProperty("");
        link = new SimpleStringProperty("");
        criteria = new SimpleStringProperty("0/0/0000");
    }
    
    public Schedule(String newType, String newDate, String newTime, String newTitle, String newTopic,
                    String newLink, String newCriteria){
        type = new SimpleStringProperty(newType);
        date = new SimpleStringProperty(newDate);
        time = new SimpleStringProperty(newTime);
        title = new SimpleStringProperty(newTitle);
        topic = new SimpleStringProperty(newTopic);
        link = new SimpleStringProperty(newLink);
        criteria = new SimpleStringProperty(newCriteria);
    }
    
    public String getType(){
        return type.get();
    }
    
    public String getDate(){
        return date.get();
    }
    
    public String getTime(){
        return time.get();
    }
    
    public String getTitle(){
        return title.get();
    }
    
    public String getTopic(){
        return topic.get();
    }
    
    public String getLink(){
        return link.get();
    }
    
    public String getCriteria(){
        return criteria.get();
    }
    
    public void setType(String newType){
        this.type.set(newType);
    }
    
    public void setDate(String newDate){
        date.set(newDate);
    }
    
    public void setTime(String newTime){
        time.set(newTime);
    }
    
    public void setTitle(String newTitle){
        title.set(newTitle);
    }
    
    public void setTopic(String newTopic){
        topic.set(newTopic);
    }
    
    public void setLink(String newLink){
        link.set(newLink);
    }
    
    public void setCriteria(String newCriteria){
        criteria.set(newCriteria);
    }
}
