/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.ScheduleData;

/**
 *
 * @author Suriri
 */
public class ScheduleRemoveUR implements jTPS_Transaction{
    String type;
    String date;
    String time;
    String title;
    String topic;
    String link;
    String criteria;
    TAManagerApp app;
    ScheduleData scheduleData;
    
    public ScheduleRemoveUR(String ntype, String ndate, String ntime, String ntitle,
            String ntopic, String nlink, String ncriteria, TAManagerApp initApp){
        type = ntype;
        date = ndate;
        time = ntime;
        title = ntitle;
        topic = ntopic;
        link = nlink;
        criteria = ncriteria;
        app = initApp;
        scheduleData = ((AllData)app.getDataComponent()).getScheduleData();
               
             
    }
    
    @Override
    public void doTransaction() {
        scheduleData.removeSchedule(type, date, time, title, topic, link, criteria);
    }

    @Override
    public void undoTransaction() {
        scheduleData.addSchedule(type, date, time, title, topic, link, criteria);
    }
    
}
