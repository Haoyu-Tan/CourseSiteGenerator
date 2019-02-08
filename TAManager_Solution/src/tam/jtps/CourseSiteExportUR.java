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
public class CourseSiteExportUR implements jTPS_Transaction{
    String newExportDir;
    String oldExportDir;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    
    public CourseSiteExportUR(String nExport, String oExport, TAManagerApp initApp){
        newExportDir = nExport;
        oldExportDir = oExport;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
    }
    
    @Override
    public void doTransaction() {
        courseSiteData.setExportDir(newExportDir);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        workspace.setExportDir(newExportDir);
        if (workspace.getExportDir().equals("")){
            workspace.getExport().setText("Choose your export directory");
        }
        else{
            workspace.getExport().setText(workspace.getExportDir());
        }
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setExportDir(oldExportDir);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        workspace.setExportDir(oldExportDir);
        if (workspace.getExportDir().equals("")){
            workspace.getExport().setText("Choose your export directory");
        }
        else{
            workspace.getExport().setText(workspace.getExportDir());
        }
    }
    
}
