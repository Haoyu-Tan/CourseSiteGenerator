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
public class CourseSiteYearUR implements jTPS_Transaction{
    String newYear;
    String oldYear;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    public static boolean isUndo = false;
    public static boolean isRedo = false;
    
    public CourseSiteYearUR(String nYear, String oYear, TAManagerApp initApp){
        newYear = nYear;
        oldYear = oYear;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
        
    }

    @Override
    public void doTransaction() {
        courseSiteData.setYear(newYear);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        if (newYear.equals("")){
            workspace.getYearCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getYearCombo().getSelectionModel().select(newYear);
        }
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setYear(oldYear);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        isUndo = true;
        if (oldYear.equals("")){
            workspace.getYearCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getYearCombo().getSelectionModel().select(oldYear);
        }
    }
    
}
