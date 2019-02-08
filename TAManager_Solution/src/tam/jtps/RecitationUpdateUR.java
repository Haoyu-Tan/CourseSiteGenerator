/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.Recitation;
import tam.data.RecitationData;
import tam.data.TeachingAssistant;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class RecitationUpdateUR implements jTPS_Transaction{
    TAManagerApp app;
    String newSection;
    String newInstructor;
    String newDay;
    String newLocation;
    String newTA1;
    String newTA2;
    String section;
    String instructor;
    String day;
    String location;
    String ta1;
    String ta2;
    RecitationData recitationData;
    TAWorkspace workspace;
    
    public RecitationUpdateUR(TAManagerApp initApp){
        this.app = initApp;
        workspace = (TAWorkspace)app.getWorkspaceComponent();
        AllData allData = (AllData)app.getDataComponent();
            
        recitationData = allData.getRecitationData();
        
        TextField sectionText = workspace.getRsectionText();
        TextField instructorText = workspace.getRinstructorText();
        TextField dayText = workspace.getrDayText();
        TextField locationText = workspace.getRlocText();
        
        newSection = sectionText.getText();
        newInstructor = instructorText.getText();
        newDay = dayText.getText();
        newLocation = locationText.getText();
        newTA1 = ((TeachingAssistant)workspace.getRta1Combo().getValue()).getName();
        newTA2 = ((TeachingAssistant)workspace.getRta2Combo().getValue()).getName();
        
        TableView recitationTable = workspace.getRecitationTable();
        
        Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
        
        Recitation recitation = (Recitation)selectedItem;
        section = recitation.getSection();
        instructor = recitation.getInstructor();
        day = recitation.getDay();
        location = recitation.getLocation();
        ta1 = recitation.getTa1();
        ta2 = recitation.getTa2();
        
        
    }

    @Override
    public void doTransaction() {
        recitationData.removeRecitation(section, instructor, day, location, ta1, ta2);
        recitationData.addRecitation(newSection, newInstructor, newDay, newLocation, newTA1, newTA2);
        
        
    }

    @Override
    public void undoTransaction() {
        recitationData.removeRecitation(newSection, newInstructor, newDay, newLocation, newTA1, newTA2);
        recitationData.addRecitation(section, instructor, day, location, ta1, ta2);
        //TableView recitationTable = workspace.getRecitationTable();
        
    }
    
}
