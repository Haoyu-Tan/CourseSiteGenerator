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
public class CourseSiteSubjectUR implements jTPS_Transaction{
    String oldSubject;
    String newSubject;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    public static boolean isUndo = false;
    public static boolean isRedo = false;
    //public static boolean isNew = false;
    
    public CourseSiteSubjectUR(String oSubject, String nSubject, TAManagerApp initApp){
        oldSubject = oSubject;
        newSubject = nSubject;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
        //isNew = true;
    }
    
    @Override
    public void doTransaction() {
        courseSiteData.setSubject(newSubject);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        if (newSubject.equals("")){
            workspace.getSubjectCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getSubjectCombo().getSelectionModel().select(newSubject);
        }
        
        
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setSubject(oldSubject);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        isUndo = true;
        
        if (oldSubject.equals("")){
            workspace.getSubjectCombo().getSelectionModel().select(null);
        }
        else{
            workspace.getSubjectCombo().getSelectionModel().select(oldSubject);
        }
    }
    
}
