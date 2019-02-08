/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import java.net.URI;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class CourseSiteImage3UR implements jTPS_Transaction{
    //StringProperty oldPath;
    URI newPath;
    URI oldPath;
    private TAManagerApp app;
    private TAWorkspace workspace;
    
    public CourseSiteImage3UR(URI newP, URI oldP,TAManagerApp initApp){
        oldPath = oldP;
        newPath = newP;
        app = initApp;
        workspace = (TAWorkspace)app.getWorkspaceComponent();
    }

    @Override
    public void doTransaction() {
            CourseSiteData courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
            String[] pathList = newPath.toString().split("/brands");
            String fileName = pathList[1];
            courseSiteData.setFileName3(fileName);
            courseSiteData.setRightImage(newPath.toString());
            //System.out.println(fileName);
            Image image = new Image(newPath.toString());
            workspace.setIV3(image);
        
    }

    @Override
    public void undoTransaction() {
        CourseSiteData courseSiteData = ((AllData) app.getDataComponent()).getCourseSiteData();
        String[] pathList = oldPath.toString().split("/brands");
        String fileName = pathList[1];
        System.out.println(fileName);
        courseSiteData.setFileName3(fileName);
        courseSiteData.setRightImage(oldPath.toString());
        Image image = new Image(oldPath.toString());
        workspace.setIV3(image);
    }
    
}
