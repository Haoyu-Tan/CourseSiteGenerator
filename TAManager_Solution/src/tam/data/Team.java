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
public class Team {
    StringProperty teamName;
    StringProperty color;
    StringProperty textColor;
    StringProperty link;
    
    public Team(){
        teamName = new SimpleStringProperty("");
        color = new SimpleStringProperty("");
        textColor = new SimpleStringProperty("");
        link = new SimpleStringProperty("");
    }
    
    public Team(String name, String newColor, String newTextColor, String newLink){
        this.teamName = new SimpleStringProperty(name);
        color = new SimpleStringProperty(newColor);
        textColor = new SimpleStringProperty(newTextColor);
        link = new SimpleStringProperty(newLink);
        
    }
    
    public String getTeamName(){
        return teamName.get();
    }
    
    public String getColor(){
        return color.get();
    }
    
    public String getTextColor(){
        return textColor.get();
    }
    
    public String getLink(){
        return link.get();
    }
    
    public void setTeamName(String newName){
        teamName.set(newName);
    }
    
    public void setColor(String newColor){
        color.set(newColor);
    }
    
    public void setTextColor(String newTextColor){
        textColor.set(newTextColor);
    }
    
    public void setLink(String newLink){
        link.set(newLink);
    }
    
    public String toString(){
        return teamName.getValue();
    }
}
