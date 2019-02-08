/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package djf.controller;

import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.stage.DirectoryChooser;
import properties_manager.PropertiesManager;

/**
 *
 * @author Suriri
 */
public class NewClass {
    public void handleExportRequest() {
	// WE'LL NEED THIS TO GET CUSTOM STUFF
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        String newPath = "..\\TAManagerTester\\public_html\\js\\OfficeHoursGridData.json";
        File target = new File(newPath);
        saveWork(target);
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(PATH_WORK));
        String path = dc.showDialog(null).getPath();
        System.out.println(path);
        String oldPath = "..\\TAManagerTester\\public_html";
        copy(oldPath, path);
    }
    
    public void copy(String oldPath, String newPath) { 
        try { 
            (new File(newPath)).mkdirs();
            File old =new File(oldPath); 
            String[] file = old.list(); 
            File targget = null; 
            for (int i = 0; i < file.length; i++) { 
                if(oldPath.endsWith(File.separator))
                    targget = new File(oldPath+file[i]); 
                else
                    targget = new File(oldPath+File.separator+file[i]); 
                if(targget.isFile()){ 
                    FileInputStream input = new FileInputStream(targget); 
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (targget.getName()).toString()); 
                    byte[] b = new byte[1024 * 5]; 
                    int templength; 
                    while ( (templength = input.read(b)) != -1)
                        output.write(b, 0, templength); 
                    output.flush(); 
                    output.close(); 
                    input.close(); 
                } 
                if(targget.isDirectory())
                    copy(oldPath+"/"+file[i],newPath+"/"+file[i]); 
            } 
        } 
        catch (Exception e) {  
            e.printStackTrace(); 
        } 
    }
    
    public static void saveWork(File fileName){
        
    }
    
}
