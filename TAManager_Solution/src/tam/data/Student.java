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
public class Student {
    StringProperty firstName;
    StringProperty lastName;
    StringProperty team;
    StringProperty role;
    public Student(){
        firstName = new SimpleStringProperty("");
        lastName = new SimpleStringProperty("");
        team = new SimpleStringProperty("");
        role = new SimpleStringProperty("");
    }
    
    public Student(String newFirstName, String newLastName, String newTeam, String newRole){
        firstName = new SimpleStringProperty(newFirstName);
        lastName = new SimpleStringProperty(newLastName);
        team = new SimpleStringProperty(newTeam);
        role = new SimpleStringProperty(newRole);
    }
    
    public String getFirstName(){
        return firstName.get();
    }
    
    public String getLastName(){
        return lastName.get();
    }
    
    public String getTeam(){
        return team.get();
        
    }
    
    public String getRole(){
        return role.get();
    }
    
    public void setFirstName(String newFirstName){
        firstName.set(newFirstName);
    }
    
    public void setLastName(String newLastName){
        lastName.set(newLastName);
    }
    
    public void setTeam(String newTeam){
        team.set(newTeam);
    }
    
    public void setRole(String newRole){
        role.set(newRole);
    }
}
