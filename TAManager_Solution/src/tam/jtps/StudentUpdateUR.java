/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.ProjectData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class StudentUpdateUR implements jTPS_Transaction{
    String newFirstName;
    String newLastName;
    String newTeam;
    String newRole;
    
    String oldFirstName;
    String oldLastName;
    String oldTeam;
    String oldRole;
    
    TAManagerApp app;
    ProjectData projectData;
           
    
    public StudentUpdateUR(String newFirst, String newLast, String newT, String newR,
            String oldFirst, String oldLast, String oldT, String oldR, TAManagerApp initApp){
        newFirstName = newFirst;
        newLastName = newLast;
        newTeam = newT;
        newRole = newR;
        
        oldFirstName = oldFirst;
        oldLastName = oldLast;
        oldTeam = oldT;
        oldRole = oldR;
        
        app = initApp;
        projectData = ((AllData)app.getDataComponent()).getProjectData();
    }
    
    @Override
    public void doTransaction() {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        projectData.addStudent(newFirstName, newLastName, newTeam, newRole);
        projectData.removeStudent(oldFirstName, oldLastName, oldTeam, oldRole);
        workspace.getStudentTable().getSelectionModel().select(projectData.getAStudent(newFirstName, newLastName));
    }

    @Override
    public void undoTransaction() {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        projectData.addStudent(oldFirstName, oldLastName, oldTeam, oldRole);
        projectData.removeStudent(newFirstName, newLastName, newTeam, newRole);
        workspace.getStudentTable().getSelectionModel().select(projectData.getAStudent(oldFirstName, oldLastName));
    }
    
}
