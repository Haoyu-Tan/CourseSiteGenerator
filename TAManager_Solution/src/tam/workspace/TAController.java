package tam.workspace;

import djf.controller.AppFileController;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppGUI;
import static tam.TAManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.style.TAStyle;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static tam.style.TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import tam.workspace.TAWorkspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import tam.jtps.jTPS;
import tam.jtps.jTPS_Transaction;
import tam.TAManagerProp;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.data.ProjectData;
import tam.data.Recitation;
import tam.data.RecitationData;
import tam.data.Schedule;
import tam.data.ScheduleData;
import tam.data.Student;
import tam.data.Team;
import tam.file.TimeSlot;
import tam.jtps.CourseSiteCSSUR;
import tam.jtps.CourseSiteExportUR;
import tam.jtps.CourseSiteImage1UR;
import tam.jtps.CourseSiteImage2UR;
import tam.jtps.CourseSiteImage3UR;
import tam.jtps.CourseSiteInstructorHomeUR;
import tam.jtps.CourseSiteInstructorNameUR;
import tam.jtps.CourseSiteNumberUR;
import tam.jtps.CourseSiteSemesterUR;
import tam.jtps.CourseSiteSubjectUR;
import tam.jtps.CourseSiteTemplateUR;
import tam.jtps.CourseSiteTitleUR;
import tam.jtps.CourseSiteYearUR;
import tam.jtps.RecitationAdderUR;
import tam.jtps.RecitationDeleteUR;
import tam.jtps.RecitationUpdateUR;
import tam.jtps.ScheduleAddUR;
import tam.jtps.ScheduleEndDateUR;
import tam.jtps.ScheduleRemoveUR;
import tam.jtps.ScheduleStartDateUR;
import tam.jtps.ScheduleUpdateUR;
import tam.jtps.StudentAdderUR;
import tam.jtps.StudentRemoveUR;
import tam.jtps.StudentUpdateUR;
import tam.jtps.TAAdderUR;
import tam.jtps.TAReplaceUR;
import tam.jtps.TAdeletUR;
import tam.jtps.TAhourschangeUR;
import tam.jtps.TAtoggleUR;
import tam.jtps.TeamAdderUR;
import tam.jtps.TeamRemoveUR;
import tam.jtps.TeamUpdateUR;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class TAController {
    static jTPS jTPS = new jTPS();
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    TAManagerApp app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        EmailValidator ev = new EmailValidator();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = ((AllData) app.getDataComponent()).getTAData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        else if (!ev.validate(email)){
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_EMAIL_INVALID_TITLE.toString()), props.getProperty(TA_EMAIL_INVALID_MESSAGE.toString()));
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            
            // ADD THE NEW TA TO THE DATA
            jTPS_Transaction addTAUR = new TAAdderUR(app);
            jTPS.addTransaction(addTAUR);
            
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    /**
     * This function provides a response for when the user presses a
     * keyboard key. Note that we're only responding to Delete, to remove
     * a TA.
     * 
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.BACK_SPACE) {
            // GET THE TABLE
            TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                String taName = ta.getName();
                TAData data = ((AllData) app.getDataComponent()).getTAData();
                
                jTPS_Transaction deletUR = new TAdeletUR(app, taName);
                jTPS.addTransaction(deletUR);
                
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }
    
    public void handleKeyPress2() {
        
            // GET THE TABLE
            TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                String taName = ta.getName();
                TAData data = ((AllData) app.getDataComponent()).getTAData();
                
                jTPS_Transaction deletUR = new TAdeletUR(app, taName);
                jTPS.addTransaction(deletUR);
                
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        
    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            TAData data = ((AllData) app.getDataComponent()).getTAData();
            String cellKey = pane.getId();
            
            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            jTPS_Transaction toggleUR = new TAtoggleUR(taName, cellKey, data);
            jTPS.addTransaction(toggleUR);
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        TAData data = ((AllData) app.getDataComponent()).getTAData();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        TAData data = ((AllData) app.getDataComponent()).getTAData();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        
        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);
        
        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }
    
    public void Undo(){
        jTPS.undoTransaction();
        markWorkAsEdited();
    }
    public void Redo(){
        jTPS.doTransaction();
        markWorkAsEdited();
    }
    
    public void handleImageButton1() throws URISyntaxException{
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PATH_WORK + File.separator + "brands"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showOpenDialog(null);
        
        if (workspace.getFirstImage1()){
            URI oldURI = new URI("file:/Users/Suriri/Documents/cse219/Haoyu%20Tan%20HW5/TAManager_Solution/work/brands/drink.png");
            CourseSiteImage1UR imageTrans = new CourseSiteImage1UR(file.toURI(), oldURI, app);
            jTPS.addTransaction(imageTrans);
            markWorkAsEdited();
            workspace.setFirstImage1(false);
        }
        else{
            CourseSiteData data = ((AllData) app.getDataComponent()).getCourseSiteData();
            URI oldURI = new URI(data.getSchoolImage());
            CourseSiteImage1UR imageTrans = new CourseSiteImage1UR(file.toURI(), oldURI, app);
            jTPS.addTransaction(imageTrans);
            markWorkAsEdited();
        }
         
    }
    
    public void handleImageButton2() throws URISyntaxException{
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PATH_WORK + File.separator + "brands"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showOpenDialog(null);
        
        if (workspace.getFirstImage2()){
            URI oldURI = new URI("file:/Users/Suriri/Documents/cse219/Haoyu%20Tan%20HW5/TAManager_Solution/work/brands/pizza.png");
            CourseSiteImage2UR imageTrans = new CourseSiteImage2UR(file.toURI(), oldURI, app);
            jTPS.addTransaction(imageTrans);
            markWorkAsEdited();
            workspace.setFirstImage2(false);
        }
        else{
            CourseSiteData data = ((AllData) app.getDataComponent()).getCourseSiteData();
            URI oldURI = new URI(data.getLeftImage());
            CourseSiteImage2UR imageTrans = new CourseSiteImage2UR(file.toURI(), oldURI, app);
            jTPS.addTransaction(imageTrans);
            markWorkAsEdited();
        }
         
    }
    
    public void handleImageButton3() throws URISyntaxException{
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PATH_WORK + File.separator + "brands"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showOpenDialog(null);
        
        if (workspace.getFirstImage3()){
            URI oldURI = new URI("file:/Users/Suriri/Documents/cse219/Haoyu%20Tan%20HW5/TAManager_Solution/work/brands/cake.png");
            CourseSiteImage3UR imageTrans = new CourseSiteImage3UR(file.toURI(), oldURI, app);
            jTPS.addTransaction(imageTrans);
            markWorkAsEdited();
            workspace.setFirstImage3(false);
        }
        else{
            CourseSiteData data = ((AllData) app.getDataComponent()).getCourseSiteData();
            URI oldURI = new URI(data.getRightImage());
            CourseSiteImage3UR imageTrans = new CourseSiteImage3UR(file.toURI(), oldURI, app);
            jTPS.addTransaction(imageTrans);
            markWorkAsEdited();
        }
         
    }
    
    public void changeTime(){
        TAData data = ((AllData) app.getDataComponent()).getTAData();
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox comboBox1 = workspace.getOfficeHour(true);
        ComboBox comboBox2 = workspace.getOfficeHour(false);
        boolean choice = true;
        int startTime = data.getStartHour();
        int endTime = data.getEndHour();
        int newStartTime = comboBox1.getSelectionModel().getSelectedIndex();
        int newEndTime = comboBox2.getSelectionModel().getSelectedIndex();
        if(newStartTime > endTime || newEndTime < startTime){
            comboBox1.getSelectionModel().select(startTime);
            comboBox2.getSelectionModel().select(endTime);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(TAManagerProp.START_OVER_END_TITLE.toString()), props.getProperty(TAManagerProp.START_OVER_END_MESSAGE.toString()));
            return;
        }
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);
        if(officeHours.isEmpty()){
            workspace.getOfficeHoursGridPane().getChildren().clear();
            data.initHours("" + newStartTime, "" + newEndTime);
            return;
        }
        String firsttime = officeHours.get(0).getTime();
        int firsthour = Integer.parseInt(firsttime.substring(0, firsttime.indexOf('_')));
        if(firsttime.contains("pm"))
            firsthour += 12;
        if(firsttime.contains("12"))
            firsthour -= 12;
        String lasttime = officeHours.get(officeHours.size() - 1).getTime();
        int lasthour = Integer.parseInt(lasttime.substring(0, lasttime.indexOf('_')));
        if(lasttime.contains("pm"))
            lasthour += 12;
        if(lasttime.contains("12"))
            lasthour -= 12;
        if(firsthour < newStartTime || lasthour + 1 > newEndTime){
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(TAManagerProp.CONFIRM_MESSAGE_TITLE.toString()), props.getProperty(TAManagerProp.CONFIRM_MESSAGE_MESSAGE.toString()));
            String selection = yesNoDialog.getSelection();
            if (!selection.equals(AppYesNoCancelDialogSingleton.YES)){
                comboBox1.getSelectionModel().select(startTime);
                comboBox2.getSelectionModel().select(endTime);
                choice = false;
            }
        }
        if (choice){
            jTPS_Transaction changeTimeUR = new TAhourschangeUR(app);
            jTPS.addTransaction(changeTimeUR);

            markWorkAsEdited();
        }
    }
    
    public void changeExistTA(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TAData data = ((AllData) app.getDataComponent()).getTAData();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String name = ta.getName();
        String email = ta.getEmail();
        String newName = workspace.getNameTextField().getText();
        String newEmail = workspace.getEmailTextField().getText();
        
        EmailValidator ev = new EmailValidator();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (newName.equals("") || newEmail.equals("")){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	        dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE)); 
 
        }
          
        else{
            
            if (!ev.validate(newEmail)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_EMAIL_INVALID_TITLE.toString()), props.getProperty(TA_EMAIL_INVALID_MESSAGE.toString()));
            } 
            else if(!newName.equals(name) && !newEmail.equals(email) && data.containsTA(newName, newEmail)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));  
            }
            else if(data.containsEmail(newEmail) && !newEmail.equals(email)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));  
            }
            else if(data.containsName(newName) && !newName.equals(name)){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));  
            }
            
            else {
                jTPS_Transaction replaceTAUR = new TAReplaceUR(app);
                jTPS.addTransaction(replaceTAUR);
                markWorkAsEdited();
                workspace.getNameTextField().clear();
                workspace.getEmailTextField().clear();
                
            }
            
        }
    }
    
    
    public void loadTAtotext(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String name = ta.getName();
            String email = ta.getEmail();
            workspace.getNameTextField().setText(name);
            workspace.getEmailTextField().setText(email);
        }
    }
    
    public void loadStudenttoText(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView studentTable = workspace.getStudentTable();
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null){
            Student student = (Student)selectedItem;
            
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            String teamName = student.getTeam();
            String role = student.getRole();
            
            ProjectData projectData = ((AllData)app.getDataComponent()).getProjectData();
            Team team = projectData.getTeam(teamName);
            
            workspace.getTeamNameCombo().getSelectionModel().select(team);
            
            workspace.getFirstNameText().setText(firstName);
            workspace.getLastNameText().setText(lastName);
            workspace.getRoleText().setText(role);
            
        }
    }
    
    public void loadScheduletotext(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView scheduleTable = workspace.getScheduleTable();
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null){
            Schedule schedule = (Schedule)selectedItem;
            
            String type = schedule.getType();
            String date = schedule.getDate();
            String time = schedule.getTime();
            String title = schedule.getTitle();
            String topic = schedule.getTopic();
            String link = schedule.getLink();
            String criteria = schedule.getCriteria();
            
            workspace.getTimeText().setText(time);
            workspace.getScheduleTitleText().setText(title);
            workspace.getTopicText().setText(topic);
            workspace.getLinkText().setText(link);
            workspace.getCriteriaText().setText(criteria);
            
            workspace.getScheduleTypeCombo().getSelectionModel().select(type);
            
            String[] dateList = date.split("/");
            int[] dateL = new int[3];
            
            for (int i = 0; i < 3; i++){
                dateL[i] = Integer.parseInt(dateList[i]);
            }
            
            
            workspace.getScheduleDate().setValue(LocalDate.of(dateL[2], dateL[0], dateL[1]));
        }
    }
    
    public void loadRecitationtotext(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView recitationTable = workspace.getRecitationTable();
        Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            Recitation recitation = (Recitation)selectedItem;
            String section = recitation.getSection();
            String instructor = recitation.getInstructor();
            String day = recitation.getDay();
            String location = recitation.getLocation();
            String TA1 = recitation.getTa1();
            String TA2 = recitation.getTa2();
            
            TextField sectionText = workspace.getRsectionText();
            TextField instructorText = workspace.getRinstructorText();
            TextField dayText = workspace.getrDayText();
            TextField locationText = workspace.getRlocText();
            
            sectionText.setText(section);
            instructorText.setText(instructor);
            dayText.setText(day);
            locationText.setText(location);
            
            TAData data = ((AllData)app.getDataComponent()).getTAData();
            TeachingAssistant ta1 = data.getTA(TA1);
            TeachingAssistant ta2 = data.getTA(TA2);
                    
            
            ChoiceBox rta1Combo = workspace.getRta1Combo();
            ChoiceBox rta2Combo = workspace.getRta2Combo();
            
            
            
            rta1Combo.getSelectionModel().select(ta1);
            rta2Combo.getSelectionModel().select(ta2);
            
            
        }
    }
    
    public void handleAddRecitation(String TA1, String TA2){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField sectionText = workspace.getRsectionText();
        TextField instructorText = workspace.getRinstructorText();
        TextField dayText = workspace.getrDayText();
        TextField locationText = workspace.getRlocText();
        
        String section = sectionText.getText();
        String instructor = instructorText.getText();
        String day = dayText.getText();
        String location = locationText.getText();
        
        RecitationData recitationData = ((AllData)app.getDataComponent()).getRecitationData();
        
        
        jTPS_Transaction RecitationAdderUR = new RecitationAdderUR(section, instructor, day, location, TA1, TA2, recitationData);
        jTPS.addTransaction(RecitationAdderUR);
        markWorkAsEdited();
        sectionText.clear();
        instructorText.clear();
        dayText.clear();
        locationText.clear();
        
        
    }
    
    public void handleDeleteRecitation(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView recitationTable = workspace.getRecitationTable();
        
        Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null){
            Recitation recitation = (Recitation)selectedItem;
            String section = recitation.getSection();
            String instructor = recitation.getInstructor();
            String day = recitation.getDay();
            String location = recitation.getLocation();
            String ta1 = recitation.getTa1();
            String ta2 = recitation.getTa2();
            
            AllData allData = (AllData)app.getDataComponent();
            
            RecitationData recitationData = allData.getRecitationData();
            
            jTPS_Transaction RecitationDeleteUR = new RecitationDeleteUR(section, instructor, day, location, ta1, ta2, recitationData);
            jTPS.addTransaction(RecitationDeleteUR);
            markWorkAsEdited();
        }
        
    }
    
    public void handleUpdateSchedule(String type, String date){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        
        AllData allData = (AllData)app.getDataComponent();
        ScheduleData scheduleData = allData.getScheduleData();
        
        TableView scheduleTable = workspace.getScheduleTable();
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        Schedule schedule = (Schedule)selectedItem;
        
        String oType = schedule.getType();
        String oDate = schedule.getDate();
        String oTime = schedule.getTime();
        String oTitle = schedule.getTitle();
        String oTopic = schedule.getTopic();
        String oLink = schedule.getLink();
        String oCriteria = schedule.getCriteria();
        
        String newType = type;
        String[] dateList = date.split("-");
        String newDate = dateList[1] + "/" + dateList[2] + "/" + dateList[0];
        
        TextField timeText = workspace.getTimeText();
        TextField titleText = workspace.getScheduleTitleText();
        TextField topicText = workspace.getTopicText();
        TextField linkText = workspace.getLinkText();
        TextField criteriaText = workspace.getCriteriaText();
            
        String newTime = timeText.getText();
        String newTitle = titleText.getText();
        String newTopic = topicText.getText();
        String newLink = linkText.getText();
        String newCriteria = criteriaText.getText();
        
        if ((!oType.equals(newType) || !oDate.equals(newDate) || !oTime.equals(newTime) ||
                !oTitle.equals(newTitle) || !oTopic.equals(newTopic) || !oLink.equals(newLink)
                || !oCriteria.equals(newCriteria)) && scheduleData.isContains(newType, newDate, newTime,
                        newTitle, newTopic, newLink, newCriteria)){
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SAME_SCHEDULE_TITLE), props.getProperty(SAME_SCHEDULE_TEXT));
            
        }
        else{
            jTPS_Transaction ScheduleUpdateUR = new ScheduleUpdateUR(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria,
            oType, oDate, oTime, oTitle, oTopic, oLink, oCriteria, app);
            jTPS.addTransaction(ScheduleUpdateUR);
            markWorkAsEdited();
        }
        
    }
    
    
    public void handleUpdateRecitation(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        
        AllData allData = (AllData)app.getDataComponent();
            
        RecitationData recitationData = allData.getRecitationData();
        
        TextField sectionText = workspace.getRsectionText();
        TextField instructorText = workspace.getRinstructorText();
        TextField dayText = workspace.getrDayText();
        TextField locationText = workspace.getRlocText();
        
        String newSection = sectionText.getText();
        String newInstructor = instructorText.getText();
        String newDay = dayText.getText();
        String newLocation = locationText.getText();
        String TA1 = ((TeachingAssistant)workspace.getRta1Combo().getValue()).getName();
        String TA2 = ((TeachingAssistant)workspace.getRta2Combo().getValue()).getName();
        
        TableView recitationTable = workspace.getRecitationTable();
        
        Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
        
        Recitation recitation = (Recitation)selectedItem;
        String section = recitation.getSection();
        String instructor = recitation.getInstructor();
        String day = recitation.getDay();
        String location = recitation.getLocation();
        String ta1 = recitation.getTa1();
        String ta2 = recitation.getTa2();
        
        if (!newSection.equals(section) && !newInstructor.equals(instructor) && !newDay.equals(day) &&
                !newLocation.equals(location) && !TA1.equals(ta1) && !TA2.equals(ta2)){
                if(recitationData.isContains(newSection, newInstructor, newDay, newLocation, TA1, TA2)){
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(SAME_RECITATION_TITLE), props.getProperty(SAME_RECITATION_TEXT));
                }
        }
        else if(recitationData.isContains(newSection, newInstructor, newDay, newLocation, TA1, TA2)){
            if (!newSection.equals(section) || !newInstructor.equals(instructor) || !newDay.equals(day) ||
                !newLocation.equals(location) || !TA1.equals(ta1) || !TA2.equals(ta2)){
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(SAME_RECITATION_TITLE), props.getProperty(SAME_RECITATION_TEXT));
            }
        }
        
        else{
            jTPS_Transaction RecitationUpdateUR = new RecitationUpdateUR(app);
            jTPS.addTransaction(RecitationUpdateUR);
            markWorkAsEdited();
        }
        
        
        recitationTable.getSelectionModel().select(recitationData.getRecitation(newSection, newInstructor, newDay,
        newLocation, TA1, TA2));
        
    }
    
    public void handleSelectCSS(){
        if (CourseSiteCSSUR.isChanged){
            CourseSiteCSSUR.isChanged = false;
        }
        else {
            TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
            AllData allData = (AllData) app.getDataComponent();
            CourseSiteData courseSiteData = allData.getCourseSiteData();
            Object selectedItem = workspace.getSSCombo().getSelectionModel().getSelectedItem();
            String newCSS = (String) selectedItem;
            String oldPath = courseSiteData.getStyleSheet();
            String oldCSS = "";
            System.out.println("old Path is : " + oldPath);

            for (String key : courseSiteData.getCSSInfo().keySet()) {
                if (courseSiteData.getCSSInfo().get(key).equals(oldPath)) {
                    oldCSS = key;
                }
            }

            jTPS_Transaction CourseSiteCSSUR = new CourseSiteCSSUR(newCSS, oldCSS, app);
            jTPS.addTransaction(CourseSiteCSSUR);
            markWorkAsEdited();
        }
        
    }
    
    public void handleSelectSubject(){
        System.out.println("project is here");
        if (CourseSiteSubjectUR.isRedo){
            CourseSiteSubjectUR.isRedo = false;
        }
        //else if (CourseSiteSubjectUR.isNew){
          //  CourseSiteSubjectUR.isNew = false;
        //}
        else{
            TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
            AllData allData = (AllData) app.getDataComponent();
            CourseSiteData courseSiteData = allData.getCourseSiteData();
            Object selectedItem = workspace.getSubjectCombo().getSelectionModel().getSelectedItem();
            String newSubject = (String)selectedItem;
            String oldSubject = courseSiteData.getSubject();
            
            jTPS_Transaction CourseSiteSubjectUR = new CourseSiteSubjectUR(oldSubject, newSubject, app);
            jTPS.addTransaction(CourseSiteSubjectUR);
            markWorkAsEdited();
        }
    }
    
    public void handleSelectNumber(){
        System.out.println("here");
        if (CourseSiteNumberUR.isRedo){
            CourseSiteNumberUR.isRedo = false;
        }
        else{
            TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
            AllData allData = (AllData) app.getDataComponent();
            CourseSiteData courseSiteData = allData.getCourseSiteData();
            Object selectedItem = workspace.getCourseNumCombo().getSelectionModel().getSelectedItem();
            String newNum = (String)selectedItem;
            String oldNum = courseSiteData.getNumber();
            
            jTPS_Transaction CourseSiteNumberUR = new CourseSiteNumberUR(newNum, oldNum, app);
            jTPS.addTransaction(CourseSiteNumberUR);
            markWorkAsEdited();
        }
    }
    
    public void handleSelectSemester(){
        System.out.println("here");
        if (CourseSiteSemesterUR.isRedo){
            CourseSiteSemesterUR.isRedo = false;
        }
        else{
            TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
            AllData allData = (AllData) app.getDataComponent();
            CourseSiteData courseSiteData = allData.getCourseSiteData();
            Object selectedItem = workspace.getSemesterCombo().getSelectionModel().getSelectedItem();
            String newSemester = (String)selectedItem;
            String oldSemester = courseSiteData.getSemester();
            
            jTPS_Transaction CourseSiteSemesterUR = new CourseSiteSemesterUR(newSemester, oldSemester, app);
            jTPS.addTransaction(CourseSiteSemesterUR);
            markWorkAsEdited();
        }
        
        AllData allData = (AllData) app.getDataComponent();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        System.out.println(courseSiteData.getSemester());
    }
    
    public void handleSelectYear(){
        System.out.println("Year");
        if (CourseSiteYearUR.isRedo){
            CourseSiteYearUR.isRedo = false;
        }
        else{
            TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
            AllData allData = (AllData) app.getDataComponent();
            CourseSiteData courseSiteData = allData.getCourseSiteData();
            Object selectedItem = workspace.getYearCombo().getSelectionModel().getSelectedItem();
            String newYear = (String)selectedItem;
            String oldYear = courseSiteData.getYear();
            
            jTPS_Transaction CourseSiteYearUR = new CourseSiteYearUR(newYear, oldYear, app);
            jTPS.addTransaction(CourseSiteYearUR);
            markWorkAsEdited();
        }
        
        AllData allData = (AllData) app.getDataComponent();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        System.out.println("semester is " + courseSiteData.getSemester());
    }
    
    public void handleTitleText(){
        System.out.println("here");
        if (CourseSiteTitleUR.isRedo){
            CourseSiteTitleUR.isRedo = false;
        }
        else{
            TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
            AllData allData = (AllData) app.getDataComponent();
            CourseSiteData courseSiteData = allData.getCourseSiteData();
            TextField titleText = workspace.getTitleText();
            String newTitle = titleText.getText();
            String oldTitle = courseSiteData.getTitle();
            
            jTPS_Transaction CourseSiteTitleUR = new CourseSiteTitleUR(newTitle, oldTitle, app);
            jTPS.addTransaction(CourseSiteTitleUR);
            markWorkAsEdited();
        }
    }
    
    public void handleIstructorName(){
        System.out.println("InstructorName");
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        AllData allData = (AllData) app.getDataComponent();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        TextField instructorText = workspace.getInstructorNmaeText();
        String newInstructor = instructorText.getText();
        String oldInstructor = courseSiteData.getInstructorName();
        
        jTPS_Transaction CourseSiteInstructorNameUR = new CourseSiteInstructorNameUR(newInstructor, oldInstructor, app);
        jTPS.addTransaction(CourseSiteInstructorNameUR);
        markWorkAsEdited();
    }
    
    public void handleInstructorHome(){
        System.out.println("InstructorHome");
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        AllData allData = (AllData) app.getDataComponent();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        TextField HomeText = workspace.getInstructorHomeText();
        String newHome = HomeText.getText();
        String oldHome = courseSiteData.getInstructorHome();
        
        jTPS_Transaction CourseSiteInstructorHomeUR = new CourseSiteInstructorHomeUR(newHome, oldHome, app);
        jTPS.addTransaction(CourseSiteInstructorHomeUR);
        markWorkAsEdited();
    }
    
    public void handleExportButton(){
        System.out.println("Export");
        
        AllData allData = (AllData) app.getDataComponent();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(PATH_WORK));
        String newPath = dc.showDialog(null).getPath();
        System.out.println(newPath);
        
        String oldPath = courseSiteData.getExportDir();
        
        jTPS_Transaction CourseSiteExportUR = new CourseSiteExportUR(newPath, oldPath, app);
        jTPS.addTransaction(CourseSiteExportUR);
        markWorkAsEdited();
        
    }
    
    public void handleTemplateButton(){
        System.out.print("template");
        
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(PATH_WORK + File.separator + "template"));
        String newPath = dc.showDialog(null).getPath();
        System.out.println(newPath);
        
        AllData allData = (AllData) app.getDataComponent();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        
        String oldPath = courseSiteData.getTemplateDir();
        
        jTPS_Transaction CourseSiteTemplateUR = new CourseSiteTemplateUR(newPath, oldPath, app);
        jTPS.addTransaction(CourseSiteTemplateUR);
        markWorkAsEdited();
        
    }
    
    public void handleScheduleStart(String newStartW, String newEndW){
       String[] newS = newStartW.split("-");
       String[] newE = newEndW.split("-");
       String newStart = newS[1] + "/" + newS[2] + "/" + newS[0];
       String newEnd = newE[1] + "/" + newE[2] + "/" + newE[0];
       
       AllData allData = (AllData) app.getDataComponent();
       ScheduleData scheduleData = allData.getScheduleData();
       
       String oldStart = scheduleData.getStartDate();
       String oldEnd = scheduleData.getEndDate();
       
       String[] oldS = oldStart.split("/");
       String[] oldE = oldEnd.split("/");
       
       PropertiesManager props = PropertiesManager.getPropertiesManager();
       
       //if (!scheduleData.isCorrectDateV1(oldS, oldE)){
         //  AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	   //dialog.show(props.getProperty(START_DAY_OVER_END_TITLE), props.getProperty(START_DAY_OVER_END_MSG));
       //}
       if (!scheduleData.isCorrectDateV2(newS, newE)){
           AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	   dialog.show(props.getProperty(START_DAY_OVER_END_TITLE), props.getProperty(START_DAY_OVER_END_MSG));
           
           //int[] intS = new int[3];
           
           //for (int i = 0; i < 3; i++){
             //  intS[i] = Integer.parseInt(oldS[i]);
           //}
           //ScheduleStartDateUR.isRedo = true;
           //TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
           //workspace.getScheduleStart().setValue(LocalDate.of(intS[2], intS[0], intS[1]));
           
       }
       else{
           jTPS_Transaction ScheduleStartDateUR = new ScheduleStartDateUR(newStart, oldStart, newEnd, oldEnd, app);
           jTPS.addTransaction(ScheduleStartDateUR);
           markWorkAsEdited();
       }
       
    }
    
    public void handleScheduleEnd(String newStartW, String newEndW){
       String[] newS = newStartW.split("-");
       String[] newE = newEndW.split("-");
       String newStart = newS[1] + "/" + newS[2] + "/" + newS[0];
       String newEnd = newE[1] + "/" + newE[2] + "/" + newE[0];
       
       AllData allData = (AllData) app.getDataComponent();
       ScheduleData scheduleData = allData.getScheduleData();
       
       String oldStart = scheduleData.getStartDate();
       String oldEnd = scheduleData.getEndDate();
       
       String[] oldS = oldStart.split("/");
       String[] oldE = oldEnd.split("/");
       
       PropertiesManager props = PropertiesManager.getPropertiesManager();
       
       //if (!scheduleData.isCorrectDateV1(oldS, oldE)){
         //  AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	   //dialog.show(props.getProperty(START_DAY_OVER_END_TITLE), props.getProperty(START_DAY_OVER_END_MSG));
       //}
       if (!scheduleData.isCorrectDateV2(newS, newE)){
           
           AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	   dialog.show(props.getProperty(START_DAY_OVER_END_TITLE), props.getProperty(START_DAY_OVER_END_MSG));
           
           //int[] intE = new int[3];
           //for (int i = 0; i < 3; i++){
             //  intE[i] = Integer.parseInt(oldE[i]);
           //}
           //ScheduleEndDateUR.isRedo = true;
           //TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
           //workspace.getScheduleEnd().setValue(LocalDate.of(intE[2], intE[0], intE[1]));
       }
       else{
           jTPS_Transaction ScheduleEndDateUR = new ScheduleEndDateUR(newStart, oldStart, newEnd, oldEnd, app);
           jTPS.addTransaction(ScheduleEndDateUR);
           markWorkAsEdited();
       }
       
    }
    
    public void handleAddSchedule(String scheduleType, String scheduleDate){
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ScheduleData scheduleData = ((AllData)app.getDataComponent()).getScheduleData();
        
        String[] selected = scheduleDate.split("-");
        String selectedDate = selected[1] + "/" + selected[2] + "/" + selected[0];
        
        String[] selected1 = selectedDate.split("/");
        String[] startDate = scheduleData.getStartDate().split("/");
        String[] endDate = scheduleData.getEndDate().split("/");
        
        if (scheduleData.isCorrectDate(startDate, selected1) && scheduleData.isCorrectDate(selected1, endDate)){
            TextField timeText = workspace.getTimeText();
            TextField titleText = workspace.getScheduleTitleText();
            TextField topicText = workspace.getTopicText();
            TextField linkText = workspace.getLinkText();
            TextField criteriaText = workspace.getCriteriaText();
            
            String time = timeText.getText();
            String title = titleText.getText();
            String topic = topicText.getText();
            String link = linkText.getText();
            String criteria = criteriaText.getText();
            
            jTPS_Transaction ScheduleAddUR = new ScheduleAddUR(scheduleType, selectedDate, time, title, topic, link, criteria, app);
            jTPS.addTransaction(ScheduleAddUR);
            markWorkAsEdited();
   
        }
        else{
           AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	   dialog.show(props.getProperty(SCHEDULE_INVALID_TITLE), props.getProperty(SCHEDULE_INVALID_MSG));
        }
        
        
    }
    
    public void handleRemoveSchedule(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView scheduleTable = workspace.getScheduleTable();
            
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null){
            Schedule schedule = (Schedule)selectedItem;
            String type = schedule.getType();
            String date = schedule.getDate();
            String time = schedule.getTime();
            String title = schedule.getTitle();
            String topic = schedule.getTopic();
            String link = schedule.getLink();
            String criteria = schedule.getCriteria();
            
            jTPS_Transaction ScheduleRemoveUR = new ScheduleRemoveUR(type, date, time, title, topic, link, criteria, app);
            jTPS.addTransaction(ScheduleRemoveUR);
            markWorkAsEdited();
        }
    }
    
    public void handleAddTeam(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField teamNameText = workspace.getStudentNameText();
        TextField teamLinkText = workspace.getStudentLinkText();
        
        String teamName = teamNameText.getText();
        String teamColor = workspace.getColorPicker1().getValue().toString();
        String textColor = workspace.getColorPicker2().getValue().toString();
        String teamLink = teamLinkText.getText();
        
        ProjectData projectData = ((AllData)app.getDataComponent()).getProjectData();
        
        if (projectData.containsTeam(teamName, teamColor)){
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(SAME_TEAM_TITLE), props.getProperty(SAME_TEAM_TEXT));
        }
        else{
            jTPS_Transaction TeamAdderUR = new TeamAdderUR(teamName, teamColor, textColor, teamLink, app);
            jTPS.addTransaction(TeamAdderUR);
            markWorkAsEdited();
        }
        
    }
    
    public void handleAddStudent(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        ProjectData projectData = ((AllData)app.getDataComponent()).getProjectData();
        
        String firstName = workspace.getFirstNameText().getText();
        String lastName = workspace.getLastNameText().getText();
        String role = workspace.getRoleText().getText();
        Team team = (Team)workspace.getTeamNameCombo().getValue();
        String teamName = team.getTeamName();
        
        if (projectData.containsStudent(firstName, lastName)){
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(SAME_STUDENT_TITLE), props.getProperty(SAME_STUDENT_TEXT));
        }
        else{
            jTPS_Transaction StudentAdderUR = new StudentAdderUR(firstName, lastName, teamName, role, app);
            jTPS.addTransaction(StudentAdderUR);
            markWorkAsEdited();
            workspace.getFirstNameText().clear();
            workspace.getLastNameText().clear();
            workspace.getRoleText().clear();
            workspace.getTeamNameCombo().getSelectionModel().select(null);
        }
        
    }
    
    public void handleTeamRemove(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView teamTable = workspace.getTeamTable();
            
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null){
            Team team = (Team)selectedItem;
            
            String teamName = team.getTeamName();
            String teamCol = team.getColor();
            String textCol = team.getTextColor();
            String link = team.getLink();
            
            jTPS_Transaction TeamRemoveUR = new TeamRemoveUR(teamName, teamCol, textCol, link, app);
            jTPS.addTransaction(TeamRemoveUR);
            markWorkAsEdited();
        }
        
    }
    
    public void handleUpdateTeam(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView teamTable = workspace.getTeamTable();
            
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            Team team = (Team)selectedItem;
            
            String oTeamName = team.getTeamName();
            String oTeamCol = team.getColor();
            String oTextCol = team.getTextColor();
            String oLink = team.getLink();
            
            TextField teamNameText = workspace.getStudentNameText();
            TextField teamLinkText = workspace.getStudentLinkText();

            String newTeamName = teamNameText.getText();
            String newTeamColor = workspace.getColorPicker1().getValue().toString();
            String newTextColor = workspace.getColorPicker2().getValue().toString();
            String newTeamLink = teamLinkText.getText();
            
            jTPS_Transaction TeamUpdtaeUR = new TeamUpdateUR(newTeamName, newTeamColor, newTextColor, newTeamLink,
                    oTeamName, oTeamCol, oTextCol, oLink, app);
            jTPS.addTransaction(TeamUpdtaeUR);
            markWorkAsEdited();
           
            
        }
        
    }
    
    public void handleRemoveStudent(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView studentTable = workspace.getStudentTable();
            
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null){
            Student student = (Student)selectedItem;
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            String teamName = student.getTeam();
            String role = student.getRole();
            
            jTPS_Transaction StudentRemoveUR = new StudentRemoveUR(firstName, lastName, teamName, role, app);
            jTPS.addTransaction(StudentRemoveUR);
            markWorkAsEdited();
        }
        
        
    }
    
    public void handleUpdateStudent(){
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView studentTable = workspace.getStudentTable();
        ProjectData projectData = ((AllData)app.getDataComponent()).getProjectData();
            
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null){
            Student student = (Student)selectedItem;
            String ofirstName = student.getFirstName();
            String olastName = student.getLastName();
            String oteamName = student.getTeam();
            String orole = student.getRole();
            
            String firstName = workspace.getFirstNameText().getText();
            String lastName = workspace.getLastNameText().getText();
            Team team = (Team)workspace.getTeamNameCombo().getValue();
            String teamName = team.getTeamName();
            String role = workspace.getRoleText().getText();
            
            if ((!ofirstName.equals(firstName) || !olastName.equals(lastName))
                    && projectData.containsStudent(firstName, lastName)){
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(SAME_STUDENT_TITLE), props.getProperty(SAME_STUDENT_TEXT));
            }
            else{
                jTPS_Transaction StudentUpdateUR = new StudentUpdateUR(firstName, lastName, teamName, role, ofirstName,
                        olastName, oteamName, orole, app);
                jTPS.addTransaction(StudentUpdateUR);
                markWorkAsEdited();
            }
            
        }
    }
    
    public void handleAbout(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        dialog.show(props.getProperty(ABOUT_TITLE), props.getProperty(ABOUT_TEXT));
    }
}