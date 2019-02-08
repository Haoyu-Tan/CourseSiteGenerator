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
public class CourseSiteSemesterUR implements jTPS_Transaction{
    String newSemester;
    String oldSemester;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    public static boolean isUndo = false;
    public static boolean isRedo = false;

    public CourseSiteSemesterUR(String nSemester, String oSemester, TAManagerApp initApp){
        newSemester = nSemester;
        oldSemester = oSemester;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
    }
    
    @Override
    public void doTransaction() {
        courseSiteData.setSemester(newSemester);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        if (newSemester.equals("")){
            workspace.getSemesterCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getSemesterCombo().getSelectionModel().select(newSemester);
        }
        
    }

    @Override
    public void undoTransaction() {
       courseSiteData.setSemester(oldSemester);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        isUndo = true;
        if (oldSemester.equals("")){
            workspace.getSemesterCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getSemesterCombo().getSelectionModel().select(oldSemester);
        }
    }
    
}
