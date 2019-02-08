/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import tam.data.RecitationData;

/**
 *
 * @author Suriri
 */
public class RecitationDeleteUR implements jTPS_Transaction{
    String section;
    String instructor;
    String day;
    String location;
    String TA1;
    String TA2;
    RecitationData recitationData;
    
    public RecitationDeleteUR(String newSection, String newInstructor, String newDay,
            String newLocation, String newTA1, String newTA2, RecitationData data){
        section = newSection;
        instructor = newInstructor;
        day = newDay;
        location = newLocation;
        TA1 = newTA1;
        TA2 = newTA2;
        recitationData = data;
    }
    
    @Override
    public void doTransaction() {
        recitationData.removeRecitation(section, instructor, day, location, TA1, TA2);
    }

    @Override
    public void undoTransaction() {
        recitationData.addRecitation(section, instructor, day, location, TA1, TA2);
    }
    
}
