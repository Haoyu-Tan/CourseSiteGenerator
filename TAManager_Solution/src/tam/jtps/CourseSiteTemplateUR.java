/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.data.SitePage;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class CourseSiteTemplateUR implements jTPS_Transaction{
    String oldTemplate;
    String newTemplate;
    TAManagerApp app;
    CourseSiteData courseSiteData;
    ObservableList<SitePage> siteList;
    
    public CourseSiteTemplateUR(String nTemplate, String oTemplate, TAManagerApp initApp){
        newTemplate = nTemplate;
        oldTemplate = oTemplate;
        app = initApp;
        courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
        siteList = courseSiteData.getSitePage();
    }
    

    @Override
    public void doTransaction() {
        courseSiteData.setTemplateDir(newTemplate);
        siteList.clear();
        copy(newTemplate);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        courseSiteData.setTemplateDir(newTemplate);
        if (newTemplate.equals("")){
            workspace.getTemplate().setText("Choose your template directory");
        }
        else{
            workspace.getTemplate().setText(courseSiteData.getTemplateDir());
        }
        
    }

    @Override
    public void undoTransaction() {
        courseSiteData.setTemplateDir(oldTemplate);
        siteList.clear();
        copy(oldTemplate);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        courseSiteData.setTemplateDir(oldTemplate);
        if (newTemplate.equals("")){
            workspace.getTemplate().setText("Choose your template directory");
        }
        else{
            workspace.getTemplate().setText(courseSiteData.getTemplateDir());
        }
        
        
    }
    
    public void copy(String filePath){
        File file =new File(filePath); 
        String[] fileList = file.list(); 
        File targget = null; 
        for (int i = 0; i < fileList.length; i++){
            if(filePath.endsWith(File.separator))
                targget = new File(filePath + fileList[i]); 
            else
                targget = new File(filePath + File.separator + fileList[i]); 
            if(targget.isFile()){ 
                String fileName = targget.getName().toString();
                if (fileName.equals("index.html")){
                    siteList.add(new SitePage("Home", "index.html", "HomeBuilder.js", true));
                }
                else if (fileName.equals("syllabus.html")){
                    siteList.add(new SitePage("Syllabus", "syllabus.html", "SyllabusBuilder.js", true));
                }
                else if (fileName.equals("schedule.html")){
                    siteList.add(new SitePage("Schedule", "schedule.html", "ScheduleBuilder.js", true));
                }
                else if (fileName.equals("hws.html")){
                    siteList.add(new SitePage("HWs", "hws.html", "HWsBuilder.js", true));
                }
                else if (fileName.equals("projects.html")){
                    siteList.add(new SitePage("Projects", "projects.html", "ProjectsBuilder.js", true));
                }
                
            } 
            if (targget.isDirectory()) {
                copy(filePath + "/" + fileList[i]);
            }

        }
    }
    
}
