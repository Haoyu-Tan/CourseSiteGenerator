/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.ProjectData;
import tam.data.Student;

/**
 *
 * @author Suriri
 */
public class TeamRemoveUR implements jTPS_Transaction{
    String teamName;
    String teamCol;
    String textCol;
    String link;
    TAManagerApp app;
    ProjectData projectData;
    ObservableList<Student> removeStudent;
    
    public TeamRemoveUR(String nteamName, String nteamCol, String ntextCol, String nlink, TAManagerApp initApp){
        teamName = nteamName;
        teamCol = nteamCol;
        textCol = ntextCol;
        link = nlink;
        app = initApp;
        projectData = ((AllData)app.getDataComponent()).getProjectData();
        removeStudent = FXCollections.observableArrayList();
        
    }
    
    @Override
    public void doTransaction() {
        projectData.removeTeam(teamName, teamCol, textCol, link);
        ObservableList<Student> allStudent = projectData.getStudent();
        removeStudent.clear();
        //int size = allStudent.size();
        for (Student s : allStudent){
            if (s.getTeam().equals(teamName)){
                removeStudent.add(s);
                
            }
        }
        
        for (Student s : removeStudent){
            allStudent.remove(s);
        }
        
    }

    @Override
    public void undoTransaction() {
        projectData.addTeam(teamName, teamCol, textCol, link);
        ObservableList<Student> allStudent = projectData.getStudent();
        for (Student s : removeStudent){
            allStudent.add(s);
        }
    }
    
}
