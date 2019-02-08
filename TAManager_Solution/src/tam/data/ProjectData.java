/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tam.TAManagerApp;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class ProjectData {
    TAManagerApp app;
    ObservableList<Team> team;
    ObservableList<Student> student;
    public ProjectData(TAManagerApp initApp){
        app = initApp;
        team = FXCollections.observableArrayList();
        student = FXCollections.observableArrayList();
    }
    
    public ProjectData(){
        team = FXCollections.observableArrayList();
        student = FXCollections.observableArrayList();
    }
    
    public ObservableList<Team> getTeam(){
        return team;
    }
    
    public ObservableList<Student> getStudent(){
        return student;
    }
    
    public Student getAStudent(String firstName, String lastName){
        for (Student s : student){
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)){
                return s;
            }
        }
        return null;
    }
    
    public void addStudent(String newFirstName, String newLastName, String teamName, String role){
        //if (!containsStudent(newFirstName, newLastName)){
            Student s = new Student(newFirstName, newLastName, teamName, role);
            student.add(s);
        //}
    }
    
    public Team getTeam(String teamName){
        for (Team t : team){
            if (t.getTeamName().equals(teamName)){
                return t;
            }
        }
        return null;
    }
    
    
    public void removeStudent(String firstName, String lastName, String teamName, String role){
        for (int i = 0; i < student.size(); i++){
            Student s = student.get(i);
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName) &&
                    s.getTeam().equals(teamName) && s.getRole().equals(role)){
                student.remove(s);
            }
        }
    }
    
    public boolean containsStudent(String firstName, String lastName){
        for (Student s : student){
            if (s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)){
                return true;
            }
        }
        return false;
    }
    
    
    public void addTeam(String newTeam, String newColor, String newTextColor, String link){
        if (!containsTeam(newTeam)){
            Team nTeam = new Team(newTeam, newColor, newTextColor, link);
            team.add(nTeam);
        }
        
    }
    
    public boolean containsTeam(String teamName){
        for (Team t : team){
            if (t.getTeamName().equals(teamName)){
                return true;
            }
        }
        
        return false;
    }
    
    public boolean containsTeam(String teamName, String teamColor){
        for (Team t : team){
            if (t.getTeamName().equals(teamName) || t.getColor().equals(teamColor)){
                return true;
            }
        }
        return false;
    }
    
    public void removeTeam(String name, String teamCol, String textCol, String link){
        for (int i = 0; i < team.size(); i++){
            Team t = team.get(i);
            if (t.getTeamName().equals(name) && t.getColor().equals(teamCol) && t.getTextColor().equals(textCol)
                    && t.getLink().equals(link)){
                team.remove(t);
            }
        }
    }
    
    public Team getTeam(String name, String color, String textColor, String link){
        for(Team t : team){
            if(t.getTeamName().equals(name) && t.getColor().equals(color) && t.getTextColor().equals(textColor)
                    && t.getLink().equals(link)){
                return t;
            }
        }
        return null;
    }
    
    public void resetData(){
        team.clear();
        student.clear();
        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getTeamNameCombo().getSelectionModel().select(null);
    }
}
