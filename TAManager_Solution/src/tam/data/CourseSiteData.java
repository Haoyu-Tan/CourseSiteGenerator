/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;


import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import tam.TAManagerApp;
import tam.workspace.TAWorkspace;
import static tam.TAManagerProp.*;
/**
 *
 * @author Suriri
 */
public class CourseSiteData {
    TAManagerApp app;
    String subject;
    String number;
    String semester;
    String year;
    String exportDir = "";
    ObservableList<SitePage> site;
    String title;
    String instructorName;
    String instructorHome;
    String templateDir = "";
    String schoolImage;
    String leftImage;
    String rightImage;
    String styleSheet;
    String fileName1;
    String fileName2;
    String fileName3;
    String styleSheetName;
    
    
    ObservableList<String> cssFile;
    HashMap<String, String> cssInfo;
    
    public CourseSiteData(TAManagerApp initApp){
        app = initApp;
        subject = "";
        number = "";
        semester = "";
        year = "";
        site = FXCollections.observableArrayList();
        title = "";
        instructorName = "";
        instructorHome = "";
        schoolImage = "file:./images/drink.png";
        leftImage = "file:./images/pizza.png";
        rightImage = "file:./images/cake.png";
        styleSheet = "";
        cssFile = FXCollections.observableArrayList();
        cssInfo = new HashMap();
        fileName1 = "/drink.png";
        fileName2 = "/pizza.png";
        fileName3 = "/cake.png";
        styleSheetName = "";
        
    }
    
    public CourseSiteData(){
        subject = "";
        number = "";
        semester = "";
        year = "";
        site = FXCollections.observableArrayList();
        title = "";
        instructorName = "";
        instructorHome = "";
        schoolImage = "file:./images/drink.png";
        leftImage = "file:./images/pizza.png";
        rightImage = "file:./images/cake.png";
        styleSheet = "";
        cssFile = FXCollections.observableArrayList();
        cssInfo = new HashMap();
        fileName1 = "/drink.png";
        fileName2 = "/pizza.png";
        fileName3 = "/cake.png";
        styleSheetName = "";
        
    }
    
    public void setSubject(String subject){
        this.subject = subject;
    }
    
    public void setNumber(String number){
        this.number = number;
    }
    
    public void setSemester(String semester){
        this.semester = semester;
    }
    
    public void setYear(String year){
        this.year = year;
    }
    
    public void setExportDir(String exportDir){
        this.exportDir = exportDir;
    }
    
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setInstructorName(String instructorName){
        this.instructorName = instructorName;
    }
    
    public void setInstructorHome(String instructorHome){
        this.instructorHome = instructorHome;
    }
    
    public void setTemplateDir(String templateDir){
        this.templateDir = templateDir;
    }
    
    public void setSchoolImage(String schoolImage){
        this.schoolImage = schoolImage;
    }
    
    public void setLeftImage(String leftImage){
        this.leftImage = leftImage;
    }
    
    public void setRightImage(String rightImage){
        this.rightImage = rightImage;
    }
    
    public void setStyleSheet(String styleSheet){
        this.styleSheet = styleSheet;
    }
    
    public String getSubject(){
        return subject;
    }
    
    public String getNumber(){
        return number;
    }
    
    public String getSemester(){
        return semester;
    }
    
    public String getYear(){
        return year;
    }
    
    public String getExportDir(){
        return exportDir;
    }
    
    public ObservableList<SitePage> getSitePage(){
        return site;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getInstructorName(){
        return instructorName;
    }
    
    public String getInstructorHome(){
        return instructorHome;
    }
    
    public String getTemplateDir(){
        return templateDir;
    }
    
    public String getSchoolImage(){
        return schoolImage;
    }
    
    public String getLeftImage(){
        return leftImage;
    }
    
    public String getRightImage(){
        return rightImage;
    }
    
    public String getStyleSheet(){
        return styleSheet;
    }
    
    public ObservableList<String> getCSSFile(){
        return cssFile;
    }
    
    public HashMap<String, String> getCSSInfo(){
        return cssInfo;
    }
    
    
    public void addSitePage(String nav, String fileName, String script, boolean isUse){
        SitePage sitePage = new SitePage(nav, fileName, script, isUse);
        if (!containsSitePage(nav, fileName, script)){
            site.add(sitePage);
        }
        
        //Collections.sort(site);
    }
    
    public boolean containsSitePage(String nav, String fileName, String script){
        for (SitePage sp : site){
            if (sp.getNavbar().equals(nav)){
                return true;
            }
            if (sp.getFileName().equals(fileName)){
                return true;
            }
            if (sp.getScript().equals(script)){
                return true;
            }
        }
        return false;
    }
    
    public void initCourseSite(String newsubject, String newnumber, String newsemester, String newyear, String newtitle,
            String newinstructorName, String newinstructorHome, String newexportDir, String newtemplateDir, String newschoolImage,
            String newleftImage, String newrightImage, String newstyleSheet) {
        subject = newsubject;
        number = newnumber;
        semester = newsemester;
        year = newyear;
        title = newtitle;
        instructorName = newinstructorName;
        instructorHome = newinstructorHome;
        exportDir = newexportDir;
        templateDir = newtemplateDir;
        schoolImage = newschoolImage;
        leftImage = newleftImage;
        rightImage = newrightImage;
        styleSheet = newstyleSheet;
        
        //clear the site page table
        site.clear();
        
        if (app == null){
            
        }
        else{
        //initialize the choice box
        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
        
        workspaceComponent.getSubjectCombo().getSelectionModel().select(subject);
        workspaceComponent.getCourseNumCombo().getSelectionModel().select(number);
        workspaceComponent.getSemesterCombo().getSelectionModel().select(semester);
        workspaceComponent.getYearCombo().getSelectionModel().select(year);
        workspaceComponent.getSSCombo().getSelectionModel().select(styleSheet);
        
        workspaceComponent.getTitleText().setText(title);
        workspaceComponent.getInstructorNmaeText().setText(instructorName);
        workspaceComponent.getInstructorHomeText().setText(instructorHome);
        
        workspaceComponent.setExportDir(exportDir);
        workspaceComponent.setTemplateDir(templateDir);
        workspaceComponent.setImagePath1(schoolImage);
        workspaceComponent.setImagePath2(leftImage);
        workspaceComponent.setImagePath3(rightImage);
        
        workspaceComponent.getExport().setText(exportDir);
        workspaceComponent.getTemplate().setText(templateDir);
        workspaceComponent.getCSSFile().setText(styleSheet);
        }
        
        
    }
    public void setFileName1(String name1){
        fileName1 = name1;
    }
    public void setFileName2(String name2){
        fileName2 = name2;
    }
    public void setFileName3(String name3){
        fileName3 = name3;
    }
    
    public String getFileName1(){
        return fileName1;
       
    }
    
    public String getFileName2(){
        return fileName2;
       
    }
    
    public String getFileName3(){
        return fileName3;
       
    }
    
    
    
    
    public void resetData(){
        title = "";
        instructorName = "";
        instructorHome = "";
        schoolImage = "file:./images/drink.png";
        leftImage = "file:./images/pizza.png";
        rightImage = "file:./images/cake.png";
        styleSheet = "";
        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
        site.clear();
        workspaceComponent.getSubjectCombo().getSelectionModel().select(null);
        workspaceComponent.getSemesterCombo().getSelectionModel().select(null);
        workspaceComponent.getCourseNumCombo().getSelectionModel().select(null);
        workspaceComponent.getYearCombo().getSelectionModel().select(null);
        workspaceComponent.getSSCombo().getSelectionModel().select(null);
    }
    
    public void loadCSS(){
        File temp = new File(PATH_WORK);
        String rootFile = temp + File.separator + "css";
        System.out.println(rootFile);
        File[] cssScr = new File(rootFile).listFiles();
        for (int i = 0; i < cssScr.length; i++){
            String fileName = cssScr[i].getName();
            String filePath = cssScr[i].getAbsolutePath();
            System.out.println(filePath);
            if (fileName.endsWith(".css")) {
                cssInfo.put(fileName, filePath);
                cssFile.add(fileName);
            }
            
        }
        
    }
    
    public void setStyleSheetName(String name){
        styleSheetName = name;
    }
    
    public String getStyleSheetName(){
        return styleSheetName;
    }
   
}
