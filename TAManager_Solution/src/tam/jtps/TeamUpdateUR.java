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
import tam.data.Team;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class TeamUpdateUR implements jTPS_Transaction{
    String newName;
    String newTeamCol;
    String newTextCol;
    String newLink;
    String oldName;
    String oldTeamCol;
    String oldTextCol;
    String oldLink;
    TAManagerApp app;
    ProjectData projectData;
    ObservableList<Student> changedStudent;
    
    public TeamUpdateUR(String nName, String nTeamCol, String nTextCol, String nLink,
            String oName, String oTeamCol, String oTextCol, String oLink, TAManagerApp initApp){
        newName = nName;
        newTeamCol = nTeamCol;
        newTextCol = nTextCol;
        newLink = nLink;
        oldName = oName;
        oldTeamCol = oTeamCol;
        oldTextCol = oTextCol;
        oldLink = oLink;
        app = initApp;
        projectData = ((AllData)app.getDataComponent()).getProjectData();
        changedStudent = FXCollections.observableArrayList();
    }
    
    @Override
    public void doTransaction() {
        projectData.addTeam(newName, newTeamCol, newTextCol, newLink);
        projectData.removeTeam(oldName, oldTeamCol, oldTextCol, oldLink);
        ObservableList<Student> allStudent = projectData.getStudent();
        for (Student s : allStudent){
            if (s.getTeam().equals(oldName)){
                s.setTeam(newName);
            }
        }
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        workspace.getStudentTable().refresh();
        
        ProjectData projectData = ((AllData)app.getDataComponent()).getProjectData();
        workspace.getTeamTable().getSelectionModel().select(projectData.getTeam(newName, newTeamCol, newTextCol, newLink));
    }

    @Override
    public void undoTransaction() {
        projectData.addTeam(oldName, oldTeamCol, oldTextCol, oldLink);
        projectData.removeTeam(newName, newTeamCol, newTextCol, newLink);
        ObservableList<Student> allStudent = projectData.getStudent();
        for (Student s : allStudent){
            if (s.getTeam().equals(newName)){
                s.setTeam(oldName);
            }
        }
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        workspace.getStudentTable().refresh();
        
        ProjectData projectData = ((AllData)app.getDataComponent()).getProjectData();
        workspace.getTeamTable().getSelectionModel().select(projectData.getTeam(oldName, oldTeamCol, oldTextCol, oldLink));
        
    }
    
}
