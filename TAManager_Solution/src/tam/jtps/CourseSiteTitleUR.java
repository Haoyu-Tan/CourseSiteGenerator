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
public class CourseSiteTitleUR implements jTPS_Transaction{
    String newTitle;
    String oldTitle;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    public static boolean isUndo = false;
    public static boolean isRedo = false;
    
    public CourseSiteTitleUR(String nTitle, String oTitle, TAManagerApp initApp){
        newTitle = nTitle;
        oldTitle = oTitle;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
    }

    @Override
    public void doTransaction() {
        courseSiteData.setTitle(newTitle);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField titleText = workspace.getTitleText();
        if (newTitle.equals("")){
            titleText.setText("");
        }
        else{
            titleText.setText(newTitle);
        }
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setTitle(oldTitle);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField titleText = workspace.getTitleText();
        isUndo = true;
        if (oldTitle.equals("")){
            titleText.setText("");
        }
        else{
            titleText.setText(oldTitle);
        }
    }
    
    
}
