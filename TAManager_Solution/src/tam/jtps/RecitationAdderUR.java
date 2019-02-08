/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import djf.ui.AppMessageDialogSingleton;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import static tam.TAManagerProp.SAME_RECITATION_TEXT;
import static tam.TAManagerProp.SAME_RECITATION_TITLE;
import tam.data.RecitationData;

/**
 *
 * @author Suriri
 */
public class RecitationAdderUR implements jTPS_Transaction{
    String section;
    String instructor;
    String time;
    String location;
    String TA1;
    String TA2;
    RecitationData recitationData;

    public RecitationAdderUR(String newSection, String newInstructor, String newTime, String newLocation,
    String newTA1, String newTA2, RecitationData data){
        section = newSection;
        instructor = newInstructor;
        time = newTime;
        location = newLocation;
        TA1 = newTA1;
        TA2 = newTA2;
        recitationData = data;
        
    }
    
    @Override
    public void doTransaction() {
        if (!recitationData.isContains(section, instructor, time, location, TA1, TA2)){
            recitationData.addRecitation(section, instructor, time, location, TA1, TA2);
        }
        else{
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SAME_RECITATION_TITLE), props.getProperty(SAME_RECITATION_TEXT)); 
        }
        
    }

    @Override
    public void undoTransaction() {
        recitationData.removeRecitation(section, instructor, time, location, TA1, TA2);
    }
    
}
