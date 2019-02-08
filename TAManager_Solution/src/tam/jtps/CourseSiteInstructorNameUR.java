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
public class CourseSiteInstructorNameUR implements jTPS_Transaction{
    String newInstructor;
    String oldInstructor;
    TAManagerApp app;
    CourseSiteData courseSiteData;

    public CourseSiteInstructorNameUR(String nInstructor, String oInstructor, TAManagerApp initApp){
        newInstructor = nInstructor;
        oldInstructor = oInstructor;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
    }
    
    @Override
    public void doTransaction() {
        courseSiteData.setInstructorName(newInstructor);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField instructorText = workspace.getInstructorNmaeText();
        if (newInstructor.equals("")){
            instructorText.setText("");
        }
        else{
            instructorText.setText(newInstructor);
        }
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setInstructorName(oldInstructor);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField instructorText = workspace.getInstructorNmaeText();
        if (oldInstructor.equals("")){
            instructorText.setText("");
        }
        else{
            instructorText.setText(oldInstructor);
        }
    }
    
}
