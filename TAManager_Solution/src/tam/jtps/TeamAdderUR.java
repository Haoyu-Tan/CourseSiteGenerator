/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.ProjectData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class TeamAdderUR implements jTPS_Transaction{
    String teamName;
    String teamColor;
    String textColor;
    String teamLink;
    ProjectData projectData;
    TAManagerApp app;
    
    public TeamAdderUR(String nName, String nTColor, String nColor, String nLink, TAManagerApp initApp){
        teamName = nName;
        teamColor = nTColor;
        textColor = nColor;
        teamLink = nLink;
        app = initApp;
        projectData = ((AllData)app.getDataComponent()).getProjectData();
    }

    @Override
    public void doTransaction() {
        projectData.addTeam(teamName, teamColor, textColor, teamLink);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        workspace.getCircle1().setFill(Color.WHITE);
        workspace.getCircle2().setFill(Color.WHITE);
        workspace.getColorPicker1().setValue(null);
        workspace.getColorPicker2().setValue(null);
        workspace.getStudentNameText().clear();
        workspace.getStudentLinkText().clear();
    }

    @Override
    public void undoTransaction() {
        projectData.removeTeam(teamName, teamColor, textColor, teamLink);
    }
    
}
