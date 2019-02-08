/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.ProjectData;

/**
 *
 * @author Suriri
 */
public class StudentRemoveUR implements jTPS_Transaction{
    String firstName;
    String lastName;
    String teamName;
    String role;
    TAManagerApp app;
    ProjectData projectData;

    public StudentRemoveUR(String nFirstName, String nLastName, String newTeamName, String newRole, TAManagerApp initApp){
        firstName = nFirstName;
        lastName = nLastName;
        teamName = newTeamName;
        role = newRole;
        app = initApp;
        projectData = ((AllData)app.getDataComponent()).getProjectData();
    }
    
    @Override
    public void doTransaction() {
        projectData.removeStudent(firstName, lastName, teamName, role);
    }

    @Override
    public void undoTransaction() {
        projectData.addStudent(firstName, lastName, teamName, role);
    }
    
    
}
