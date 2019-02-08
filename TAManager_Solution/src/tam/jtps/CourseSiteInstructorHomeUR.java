/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import javafx.scene.control.TextField;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class CourseSiteInstructorHomeUR implements jTPS_Transaction{
    String newHome;
    String oldHome;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    
    public CourseSiteInstructorHomeUR(String nHome, String oHome, TAManagerApp initApp){
        newHome = nHome;
        oldHome = oHome;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
    }
    
    @Override
    public void doTransaction() {
        courseSiteData.setInstructorHome(newHome);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField HomeText = workspace.getInstructorHomeText();
        if (newHome.equals("")){
            HomeText.setText("");
        }
        else{
            HomeText.setText(newHome);
        }
        
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setInstructorHome(oldHome);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField HomeText = workspace.getInstructorHomeText();
        if (newHome.equals("")){
            HomeText.setText("");
        }
        else{
            HomeText.setText(oldHome);
        }
    }
    
}
