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
public class CourseSiteCSSUR implements jTPS_Transaction{
    String newCSS;
    String oldCSS;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    public static boolean isUndo = false;
    public static boolean isChanged = false;
    
    public CourseSiteCSSUR(String nCSS, String oCSS, TAManagerApp initApp){
        newCSS = nCSS;
        oldCSS = oCSS;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
    }

    @Override
    public void doTransaction() {
        
            String cssPath = courseSiteData.getCSSInfo().get(newCSS);
            
            for (String key : courseSiteData.getCSSInfo().keySet()){
                System.out.println("key is + " + key + " value is : " + courseSiteData.getCSSInfo().get(key));
            }
            
            courseSiteData.setStyleSheetName(newCSS);
            
            courseSiteData.setStyleSheet(cssPath);
            TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
            
            
            workspace.getSSCombo().getSelectionModel().select(newCSS);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        
    }

    @Override
    public void undoTransaction() {
            courseSiteData.setStyleSheetName(oldCSS);
        
            String cssPath = courseSiteData.getCSSInfo().get(oldCSS);

            courseSiteData.setStyleSheet(cssPath);
            System.out.println("CSS PATH IS   " + cssPath);
            TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
            isUndo = true;
            workspace.getSSCombo().getSelectionModel().select(oldCSS);
        
        
    }
    
}
