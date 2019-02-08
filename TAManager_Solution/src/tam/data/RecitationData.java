/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import djf.ui.AppMessageDialogSingleton;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import static tam.TAManagerProp.MISSING_TA_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TA_NAME_TITLE;
import static tam.TAManagerProp.SAME_RECITATION_TEXT;
import static tam.TAManagerProp.SAME_RECITATION_TITLE;
import tam.workspace.TAWorkspace;


/**
 *
 * @author Suriri
 */
public class RecitationData {
    TAManagerApp app;
    ObservableList<Recitation> recitation;
    public RecitationData(TAManagerApp initApp){
        app = initApp;
        recitation = FXCollections.observableArrayList();
    }
    
    public RecitationData(){
        recitation = FXCollections.observableArrayList();
    }
    
    public ObservableList<Recitation> getRecitation(){
        return recitation;
    }
    
    public void addRecitation(String newSection, String newInstructor, String newTime, String newLocation,
            String ta1, String ta2){
        Recitation rec = new Recitation(newSection, newInstructor, newTime, newLocation,
        ta1, ta2);
        
            recitation.add(rec);
        
    }
    
    public boolean isContains(String section, String instructor, String time, String location,
            String ta1, String ta2){
        for(int i = 0; i < recitation.size(); i++){
            Recitation rec = recitation.get(i);
            if (rec.getSection().equals(section)&& rec.getInstructor().equals(instructor)
                   && rec.getDay().equals(time) && rec.getLocation().equals(location) &&
                  (rec.getTa1().equals(ta1) || rec.getTa2().equals(ta2) || rec.getTa1().equals(ta2) || 
                    rec.getTa2().equals(ta1))){
                return true;
            }
        }
        
        return false;
    }
    
    
    public Recitation getRecitation(String section, String instructor, String time, String location,
            String ta1, String ta2){
        for(int i = 0; i < recitation.size(); i++){
            Recitation rec = recitation.get(i);
            if (rec.getSection().equals(section)&& rec.getInstructor().equals(instructor)
                   && rec.getDay().equals(time) && rec.getLocation().equals(location) &&
                  (rec.getTa1().equals(ta1) || rec.getTa2().equals(ta2) || rec.getTa1().equals(ta2)) || 
                    rec.getTa2().equals(ta1)){
                return rec;
            }
        }
        return null;
    }
    
    public void removeRecitation(String section, String instructor, String day, String location,
            String TA1, String TA2){
        //for (Recitation rec : recitation){
            //if (rec.getSection().equals(section) && rec.getInstructor().equals(instructor)
              //      && rec.getDay().equals(day) && rec.getLocation().equals(location) &&
                //    rec.getTa1().equals(TA1) && rec.getTa2().equals(TA2)){
                //recitation.remove(rec);
                
            //}
        //}
        
        for(int i = 0; i < recitation.size(); i++){
            Recitation rec = recitation.get(i);
            if (rec.getSection().equals(section)&& rec.getInstructor().equals(instructor)
                   && rec.getDay().equals(day) && rec.getLocation().equals(location) &&
                  rec.getTa1().equals(TA1) && rec.getTa2().equals(TA2)){
                recitation.remove(rec);
            }
        }
    }
    
    public void resetData(){
        recitation.clear();
        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getRta1Combo().getSelectionModel().select(null);
        workspaceComponent.getRta2Combo().getSelectionModel().select(null);
    }
}
