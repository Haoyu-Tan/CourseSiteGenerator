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
public class StudentAdderUR implements jTPS_Transaction{
    String firstName;
    String lastName;
    String teamName;
    String role;
    TAManagerApp app;
    ProjectData projectData;
    
    public StudentAdderUR(String nfirstName, String nlastName, String nteamName, String nrole,
            TAManagerApp initApp){
        firstName = nfirstName;
        lastName = nlastName;
        teamName = nteamName;
        role = nrole;
        app = initApp;
        projectData = ((AllData)app.getDataComponent()).getProjectData();
    }
    
    @Override
    public void doTransaction() {
        projectData.addStudent(firstName, lastName, teamName, role);
    }

    @Override
    public void undoTransaction() {
        projectData.removeStudent(firstName, lastName, teamName, role);
    }
    
}
