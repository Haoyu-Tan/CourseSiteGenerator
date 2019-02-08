/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class CourseSiteNumberUR implements jTPS_Transaction{
    String newNumber;
    String oldNumber;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    public static boolean isUndo = false;
    public static boolean isRedo = false;
    
    public CourseSiteNumberUR(String nNumber, String oNumber, TAManagerApp initApp){
        newNumber = nNumber;
        oldNumber = oNumber;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
    }
    
    @Override
    public void doTransaction() {
        courseSiteData.setNumber(newNumber);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        
        if (newNumber.equals("")){
            workspace.getCourseNumCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getCourseNumCombo().getSelectionModel().select(newNumber);
        }
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setNumber(oldNumber);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        
        isUndo = true;
        if (oldNumber.equals("")){
            workspace.getCourseNumCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getCourseNumCombo().getSelectionModel().select(oldNumber);
        }
    }
    
}
