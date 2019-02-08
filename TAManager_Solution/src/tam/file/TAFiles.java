package tam.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.data.ProjectData;
import tam.data.Recitation;
import tam.data.RecitationData;
import tam.data.Schedule;
import tam.data.ScheduleData;
import tam.data.SitePage;
import tam.data.Student;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.data.Team;
import tam.workspace.TAWorkspace;

/**
 * This class serves as the file component for the TA
 * manager app. It provides all saving and loading 
 * services for the application.
 * 
 * @author Richard McKenna
 */
public class TAFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    TAManagerApp app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    //ta data part
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_GRADUATE_TAS = "graduate_tas";
    static final String JSON_EMAIL = "email";
    static final String JSON_TADATA = "ta_data";
    //course data part
    static final String JSON_COURSEDATA = "course_data";
    static final String JSON_SUBJECT = "course_subject";
    static final String JSON_NUMBER = "course_number";
    static final String JSON_SEMESTER = "course_semester";
    static final String JSON_YEAR = "course_year";
    static final String JSON_TITLE = "course_title";
    static final String JSON_INSTRUCTORNAME = "instructor_name";
    static final String JSON_INSTRUCTORHOME = "instructor_home";
    static final String JSON_EXPORTDIR = "export_dir";
    static final String JSON_TEMPLATEDIR = "template_dir";
    static final String JSON_SITEPAGE = "site_page";
    static final String JSON_USE = "use";
    static final String JSON_UNUSE = "unused";
    static final String JSON_NAVBAR = "navbar_title";
    static final String JSON_FILENAME = "file_name";
    static final String JSON_SCRIPT = "script";
    static final String JSON_SCHOOLIMAGE = "school_image";
    static final String JSON_LEFT = "left_footer_image";
    static final String JSON_RIGHT = "right_footer_image";
    static final String JSON_STYLESHEET = "style_sheet";
    static final String JSON_IMAGE1 = "school_image";
    static final String JSON_IMAGE2 = "left_image";
    static final String JSON_IMAGE3 = "right_image";
    
    
    //recitation part
    static final String JSON_RECITATIONDATA = "recitation_data";
    static final String JSON_RECITATION = "recitation";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_RECITATIONDAY = "day/time";
    static final String JSON_LOCATION = "location";
    static final String JSON_RECTA1 = "ta1";
    static final String JSON_RECTA2 = "ta2";
    
    //schedule part
    static final String JSON_SCHEDULEDATA = "schedule_data";
    static final String JSON_STARTDATE = "start_date";
    static final String JSON_ENDDATE = "end_date";
    static final String JSON_SCHEDULE = "schedule";
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_SCUEDULETITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_LINK = "link";
    static final String JSON_CRITERIA = "criteria";
    
    //project data part
    static final String JSON_PROJECTDATA = "project_data";
    static final String JSON_TEAM = "team";
    static final String JSON_STUDENT = "student";
    static final String JSON_TEAMNAME = "team_name";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXTCOLOR = "text_color";
    static final String JSON_FIRSTNAME = "first_name";
    static final String JSON_LASTNAME = "last_name";
    static final String JSON_ROLE = "role";
    
    static final String SCHEDULE_STARTMONTH = "startingMondayMonth";
    static final String SCHEDULE_STARTDAY = "startingMondayDay";
    static final String SCHEDULE_ENDMONTH = "endingFridayMonth";
    static final String SCHEDULE_ENDDAY = "endingFridayDay";
    static final String SCHEDULE_HOLIDAYS = "holidays";
    static final String SCHEDULE_LECTURE = "lectures";
    static final String SCHEDULE_REFERENCE = "references";
    static final String SCHEDULE_RECITATION = "recitations";
    static final String SCHEDULE_HWS = "hws";
    static final String MONTH = "month";
    static final String DAY = "day";
    static final String TITLE = "title";
    static final String TOPIC = "topic";
    static final String LINK = "link";
    static final String CRITERIA = "criteria";
    
    static final String PROJECT_WORK = "work";
    static final String PROJECT_SEMESTER = "semester";
    static final String PROJECT_PROJECT = "projects";
    static final String PROJECT_NAME = "name";
    static final String PROJECT_STUDENTS = "students";
    static final String PROJECT_LINK = "link";
    static final String PROJECT_CRITERIA = "criteria";
    
    static final String RECITATION_RECITATION = "recitations";
    static final String RECITATION_SECTION = "section";
    static final String RECITATION_DAY_TIME = "day_time";
    static final String RECITATION_LOCATION = "location";
    static final String RECITATION_TA1 = "ta_1";
    static final String RECITATION_TA2 = "ta_2";
    
    static final String TEAM_TEAMS = "teams";
    static final String TEAM_NAME = "name";
    static final String TEAM_RED = "red";
    static final String TEAM_GREEN = "green";
    static final String TEAM_BLUE = "blue";
    static final String TEAM_TEXT_RED = "text_red";
    static final String TEAM_TEXT_GREEN = "text_green";
    static final String TEAM_TEXT_BLUE = "text_blue";
    
    static final String STUDENT_STUDENTS = "students";
    static final String STUDENT_LASTNAME = "lastName";
    static final String STUDENT_FIRSTNAME = "firstName";
    static final String STUDENT_TEAM = "team";
    static final String STUDENT_ROLE = "role";
    
    static final String JSON_STYLE_SHEET = "css";
    
    public TAFiles(TAManagerApp initApp) {
        app = initApp;
    }
    
    public TAFiles(){
        
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	
        AllData allData = (AllData)data;
	TAData dataManager = allData.getTAData();
        RecitationData recitationData = allData.getRecitationData();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        ScheduleData scheduleData = allData.getScheduleData();
        ProjectData projectData = allData.getProjectData();

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        //LOAD THE COURSE SITE PART
        JsonArray jsonCourseSiteArray = json.getJsonArray(JSON_COURSEDATA);
        JsonObject CourseSiteDATAJsonObject = jsonCourseSiteArray.getJsonObject(0);
        
        String subject = CourseSiteDATAJsonObject.getString(JSON_SUBJECT);
        String number = CourseSiteDATAJsonObject.getString(JSON_NUMBER);
        String semester = CourseSiteDATAJsonObject.getString(JSON_SEMESTER);
        String year = CourseSiteDATAJsonObject.getString(JSON_YEAR);
        String title = CourseSiteDATAJsonObject.getString(JSON_TITLE);
        String instructorName = CourseSiteDATAJsonObject.getString(JSON_INSTRUCTORNAME);
        String instructorHome = CourseSiteDATAJsonObject.getString(JSON_INSTRUCTORHOME);
        String exporDir = CourseSiteDATAJsonObject.getString(JSON_EXPORTDIR);
        String templateDir = CourseSiteDATAJsonObject.getString(JSON_TEMPLATEDIR);
        String schoolImage = CourseSiteDATAJsonObject.getString(JSON_SCHOOLIMAGE);
        String leftImage = CourseSiteDATAJsonObject.getString(JSON_LEFT);
        String rightImage = CourseSiteDATAJsonObject.getString(JSON_RIGHT);
        String styleSheet = CourseSiteDATAJsonObject.getString(JSON_STYLESHEET);
        
       
        
        courseSiteData.initCourseSite(subject, number, semester, year, title, instructorName,
                instructorHome, exporDir, templateDir, schoolImage, leftImage, rightImage, styleSheet);
       
        JsonArray sitePageArray = CourseSiteDATAJsonObject.getJsonArray(JSON_SITEPAGE);
        for (int i = 0; i < sitePageArray.size(); i++){
            JsonObject jsonSitePage = sitePageArray.getJsonObject(i);
            String navBar = jsonSitePage.getString(JSON_NAVBAR);
            String fileName = jsonSitePage.getString(JSON_FILENAME);
            String script = jsonSitePage.getString(JSON_SCRIPT);
            String used = jsonSitePage.getString(JSON_USE);
            if (used.equals("true")){
                courseSiteData.addSitePage(navBar, fileName, script, true);
            }
            else{
                courseSiteData.addSitePage(navBar, fileName, script, false);
            }
        }
        
        //LOAD THE TA DATA PART
        JsonArray jsonTADataArray = json.getJsonArray(JSON_TADATA);
        
	// LOAD THE START AND END HOURS
	JsonObject TADATAJsonObject = jsonTADataArray.getJsonObject(0);
        String startHour = TADATAJsonObject.getString(JSON_START_HOUR);
        
        //JsonObject endHourJsonObject = jsonTADataArray.getJsonObject(1);
        String endHour = TADATAJsonObject.getString(JSON_END_HOUR);
        //String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);
        
        
        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        if (app == null){
           HashMap<String, StringProperty> officeHours = dataManager.getOfficeHours();
            for (int row = 1; row < 49; row++) {
                for (int col = 2; col < 7; col++) {
                    officeHours.put(col + "_" + row, new SimpleStringProperty(""));
                }
        }
        }
        else{
            app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
        }

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = TADATAJsonObject.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email, true);
        }
        
        //get the graduate TA
        JsonArray jsonGradTAArray = TADATAJsonObject.getJsonArray(JSON_GRADUATE_TAS);
        for (int i = 0; i < jsonGradTAArray.size(); i++){
            JsonObject jsonTA = jsonGradTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email, false);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = TADATAJsonObject.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        //HashMap<String, StringProperty> officeHours = dataManager.getOfficeHours();
        //System.out.println(officeHours);
        //for (String s : officeHours.keySet()){
          //  System.out.println("key is " + s + " value is " + officeHours.get(s));
        //}
        
        
        //LOAD THE RECITATION PART
        JsonArray jsonRecitationDataArray = json.getJsonArray(JSON_RECITATIONDATA);
        JsonObject RecitationDataJsonObject = jsonRecitationDataArray.getJsonObject(0);
        
        JsonArray jsonRecitationArray = RecitationDataJsonObject.getJsonArray(JSON_RECITATION);
        for (int i = 0; i < jsonRecitationArray.size(); i++){
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_SECTION);
            String instructor = jsonRecitation.getString(JSON_INSTRUCTOR);
            String time = jsonRecitation.getString(JSON_TIME);
            String location = jsonRecitation.getString(JSON_LOCATION);
            String ta1 = jsonRecitation.getString(JSON_RECTA1);
            String ta2 = jsonRecitation.getString(JSON_RECTA2);
            recitationData.addRecitation(section, instructor, time, location, ta1, ta2);
        }
        
        JsonArray jsonScheduleDataArray = json.getJsonArray(JSON_SCHEDULEDATA);
        JsonObject ScheduleDataJsonObject = jsonScheduleDataArray.getJsonObject(0);
        
        String startDate = ScheduleDataJsonObject.getString(JSON_STARTDATE);
        String endDate = ScheduleDataJsonObject.getString(JSON_ENDDATE);
        scheduleData.initDate(startDate, endDate);
        
        JsonArray jsonScheduleArray = ScheduleDataJsonObject.getJsonArray(JSON_SCHEDULE);
        for (int i = 0; i < jsonScheduleArray.size(); i++){
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);
            String type = jsonSchedule.getString(JSON_TYPE);
            String date = jsonSchedule.getString(JSON_DATE);
            String scheduleTitle = jsonSchedule.getString(JSON_TITLE);
            String topic = jsonSchedule.getString(JSON_TOPIC);
            String link = jsonSchedule.getString(JSON_LINK);
            String criteria = jsonSchedule.getString(JSON_CRITERIA);
            String time = jsonSchedule.getString(JSON_TIME);
            scheduleData.addSchedule(type, date, time, scheduleTitle, topic, link, criteria);
            
        }
        
        JsonArray jsonProjectDataArray = json.getJsonArray(JSON_PROJECTDATA);
        JsonObject projectDataJsonObject = jsonProjectDataArray.getJsonObject(0);
        
        JsonArray jsonTeamArray = projectDataJsonObject.getJsonArray(JSON_TEAM);
        for (int i = 0; i < jsonTeamArray.size(); i++){
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            String team = jsonTeam.getString(JSON_TEAMNAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXTCOLOR);
            String link = jsonTeam.getString(JSON_LINK);
            projectData.addTeam(team, color, textColor, link);
        }
        
        JsonArray jsonStudentArray = projectDataJsonObject.getJsonArray(JSON_STUDENT);
        for (int i = 0; i < jsonStudentArray.size(); i++){
            JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_FIRSTNAME);
            String lastName = jsonStudent.getString(JSON_LASTNAME);
            String team = jsonStudent.getString(JSON_TEAM);
            String role = jsonStudent.getString(JSON_ROLE);
            projectData.addStudent(firstName, lastName, team, role);
        }
        
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
        AllData allData = (AllData)data;
	TAData taData = allData.getTAData();
        RecitationData recitationData = allData.getRecitationData();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        ScheduleData scheduleData = allData.getScheduleData();
        ProjectData projectData = allData.getProjectData();
        
        //BUILD THE COURSE SITE JSON OBJECT TO SAVE
        JsonArrayBuilder SitePageArrayBuilder = Json.createArrayBuilder();
        ObservableList<SitePage> sitePage = courseSiteData.getSitePage();
        for (SitePage page : sitePage){
            if (page.isUsed()){
                JsonObject sitePageJson = Json.createObjectBuilder()
                        .add(JSON_NAVBAR, page.getNavbar())
                        .add(JSON_FILENAME, page.getFileName())
                        .add(JSON_SCRIPT, page.getScript())
                        .add(JSON_USE, "true")
                        .build();
                SitePageArrayBuilder.add(sitePageJson);
            }
            else{
                JsonObject sitePageJson = Json.createObjectBuilder()
                        .add(JSON_NAVBAR, page.getNavbar())
                        .add(JSON_FILENAME, page.getFileName())
                        .add(JSON_SCRIPT, page.getScript())
                        .add(JSON_USE, "false")
                        .build();
                SitePageArrayBuilder.add(sitePageJson);
            }
           
        }
        JsonArray sitePageArray = SitePageArrayBuilder.build();
        
        //PUT EVERYTHING IN SITEPAGE INTO SITEPAGE JSON OBJECT
        JsonObject SitePageManagerJSO = Json.createObjectBuilder()
                .add(JSON_SUBJECT, courseSiteData.getSubject())
                .add(JSON_NUMBER, courseSiteData.getNumber())
                .add(JSON_SEMESTER, courseSiteData.getSemester())
                .add(JSON_YEAR, courseSiteData.getYear())
                .add(JSON_TITLE, courseSiteData.getTitle())
                .add(JSON_INSTRUCTORNAME, courseSiteData.getInstructorName())
                .add(JSON_INSTRUCTORHOME, courseSiteData.getInstructorHome())
                .add(JSON_EXPORTDIR, courseSiteData.getExportDir())
                .add(JSON_TEMPLATEDIR, courseSiteData.getTemplateDir())
                .add(JSON_SITEPAGE, sitePageArray)
                .add(JSON_SCHOOLIMAGE, courseSiteData.getSchoolImage())
                .add(JSON_LEFT, courseSiteData.getLeftImage())
                .add(JSON_RIGHT, courseSiteData.getRightImage())
                .add(JSON_STYLESHEET, courseSiteData.getStyleSheet())
                .build();
        JsonArrayBuilder courseSiteDataBuilder = Json.createArrayBuilder();
        courseSiteDataBuilder.add(SitePageManagerJSO);
        JsonArray CourseSiteDataArray = courseSiteDataBuilder.build();
        
        //RECITATION DATA
        JsonArrayBuilder RecitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitation = recitationData.getRecitation();
        for (Recitation rec : recitation){
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, rec.getSection())
                    .add(JSON_INSTRUCTOR, rec.getInstructor())
                    .add(JSON_TIME, rec.getDay())
                    .add(JSON_LOCATION, rec.getLocation())
                    .add(JSON_RECTA1, rec.getTa1())
                    .add(JSON_RECTA2, rec.getTa2())
                    .build();
            RecitationArrayBuilder.add(recitationJson);
        }
        JsonArray recitationArray = RecitationArrayBuilder.build();
        JsonObject RecitationDataManagerJSO = Json.createObjectBuilder()
                .add(JSON_RECITATION, recitationArray)
                .build();
        JsonArrayBuilder RecitationDataBuilder = Json.createArrayBuilder();
        RecitationDataBuilder.add(RecitationDataManagerJSO);
        JsonArray recitationDataArray = RecitationDataBuilder.build();
        
        //SCHEDULE DATA
        JsonArrayBuilder ScheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> scheduleList = scheduleData.getSchedule();
        for(Schedule schedule : scheduleList){
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, schedule.getType())
                    .add(JSON_DATE, schedule.getDate())
                    .add(JSON_TIME, schedule.getTime())
                    .add(JSON_TITLE, schedule.getTitle())
                    .add(JSON_TOPIC, schedule.getTopic())
                    .add(JSON_LINK, schedule.getLink())
                    .add(JSON_CRITERIA, schedule.getCriteria())
                    .build();
            ScheduleArrayBuilder.add(scheduleJson);
        }
        JsonArray scheduleArray = ScheduleArrayBuilder.build();
        JsonObject ScheduleDataManagerJSO = Json.createObjectBuilder()
                .add(JSON_STARTDATE, scheduleData.getStartDate())
                .add(JSON_ENDDATE, scheduleData.getEndDate())
                .add(JSON_SCHEDULE, scheduleArray)
                .build();
        JsonArrayBuilder scheduleDataBuilder = Json.createArrayBuilder();
        scheduleDataBuilder.add(ScheduleDataManagerJSO);
        JsonArray scheduleDataArray = scheduleDataBuilder.build();
        
        //PROJECT DATA
        JsonArrayBuilder TeamArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teamList = projectData.getTeam();
        for (Team team : teamList){
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_TEAMNAME, team.getTeamName())
                    .add(JSON_COLOR, team.getColor())
                    .add(JSON_TEXTCOLOR, team.getTextColor())
                    .add(JSON_LINK, team.getLink())
                    .build();
            TeamArrayBuilder.add(teamJson);
        }
        JsonArray teamArray = TeamArrayBuilder.build();
        
        JsonArrayBuilder StudentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> studentList = projectData.getStudent();
        for(Student student : studentList){
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_FIRSTNAME, student.getFirstName())
                    .add(JSON_LASTNAME, student.getLastName())
                    .add(JSON_TEAM, student.getTeam())
                    .add(JSON_ROLE, student.getRole())
                    .build();
            StudentArrayBuilder.add(studentJson);
        }
        JsonArray studentArray = StudentArrayBuilder.build();
        
        JsonObject projectDataManagerJSO = Json.createObjectBuilder()
                .add(JSON_TEAM, teamArray)
                .add(JSON_STUDENT, studentArray)
                .build();
        JsonArrayBuilder ProjectDataBuilder = Json.createArrayBuilder();
        ProjectDataBuilder.add(projectDataManagerJSO);
        JsonArray projectDataArray = ProjectDataBuilder.build();
        
                
        //TA DATA
	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder UndergradtaArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder GraduatetaArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = taData.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {
            if (ta.isUndergrad()){
	        JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail()).build();
                UndergradtaArrayBuilder.add(taJson);
            }
            else{
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail()).build();
                GraduatetaArrayBuilder.add(taJson);
            }
	}
	JsonArray undergradTAsArray = UndergradtaArrayBuilder.build();
        JsonArray graduateTAsArray = GraduatetaArrayBuilder.build();
        
        
        
	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	
       
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(taData);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject TADataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + taData.getStartHour())
		.add(JSON_END_HOUR, "" + taData.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_GRADUATE_TAS, graduateTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.build();
        
        JsonArrayBuilder taDataBuilder = Json.createArrayBuilder();
        taDataBuilder.add(TADataManagerJSO);
        JsonArray taDataArray = taDataBuilder.build();
        
        
        
        //ADD ALL DATA TOGETHER
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_COURSEDATA, CourseSiteDataArray)
                .add(JSON_TADATA, taDataArray)
                .add(JSON_RECITATIONDATA, recitationDataArray)
                .add(JSON_SCHEDULEDATA, scheduleDataArray)
                .add(JSON_PROJECTDATA, projectDataArray)
                .build();
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void exportSave() throws IOException{
        // WE'LL NEED THIS TO GET CUSTOM STUFF
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        AllData allData = (AllData)app.getDataComponent();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        
        //String path4 = "./work/" + File.separator + "TAManagerTester";
        
        String tempDir = courseSiteData.getTemplateDir();
        String[] part = tempDir.split("/work");
        
        String expDir = courseSiteData.getExportDir();
        String templateDir = "./work" + part[1];
        System.out.println("templateDir is " + templateDir);
        //String oldPath = "..\\TAManagerTester\\public_html";
        copy(templateDir, expDir);
        
        //File temp = new File("./work/");
        //String Path1 = temp.getAbsoluteFile().getParentFile().getParent();
        //File temp2 = new File(Path1);
        //String Path2 = temp2.getAbsoluteFile().getParentFile().getParent() + File.separator + "destination folder";
        //System.out.println(Path2);
        String path3 = expDir + File.separator + "public_html" + File.separator + "js";
        exportSaveWork(allData, path3, templateDir, expDir);
        
        
    }
    
    public void exportSaveWork(AllData allData, String baseAddr, String oldAddr, String exportDir) throws IOException{
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        ObservableList<SitePage> site = courseSiteData.getSitePage();
        
        String jsonPath0 = baseAddr + "/CourseSite.json";
        exportSitePage(jsonPath0, allData);
        
        File js1 = new File(oldAddr + File.separator + "Gruntfile.js");
        File des1 = new File(exportDir + "/Gruntfile.js");
        Files.copy(js1.toPath(), des1.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        File js2 = new File(oldAddr + File.separator + "gulpfile.js");
        File des2 = new File(exportDir + "/gulpfile.js");
        Files.copy(js2.toPath(), des2.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        copy("./work/brands", courseSiteData.getExportDir() + File.separator + "public_html" + File.separator + "images");
        
        
        for (SitePage sp : site){
            String fileName = sp.getFileName();
            if (fileName.equals("index.html") && sp.isUsed()){
                String jsonPath = baseAddr + "/TeamsAndStudents.json";
                exportTeamAndStudent(jsonPath, allData);
                File indexHTML = new File(oldAddr + File.separator + "public_html" + File.separator + "/index.html");
                File desHTML = new File(exportDir + "/public_html/index.html");
                Files.copy(indexHTML.toPath(), desHTML.toPath());
                
                File jsFile = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "TeamsBuilder.js");
                File desJS = new File(exportDir + "/public_html/js/TeamsBuilder.js");
                Files.copy(jsFile.toPath(), desJS.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
            }
            else if (fileName.equals("syllabus.html") && sp.isUsed()){
                File indexHTML = new File(oldAddr + File.separator + "public_html" + File.separator + "/syllabus.html");
                File desHTML = new File(exportDir + "/public_html/syllabus.html");
                Files.copy(indexHTML.toPath(), desHTML.toPath());
                
                File jsFile1 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "HomeBuilder.js");
                File desJS1 = new File(exportDir + "/public_html/js/HomeBuilder.js");
                Files.copy(jsFile1.toPath(), desJS1.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                File jsFile2 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "OfficeHoursGridBuilder.js");
                File desJS2 = new File(exportDir + "/public_html/js/OfficeHoursGridBuilder.js");
                Files.copy(jsFile2.toPath(), desJS2.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                File jsFile3 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "RecitationsBuilder.js");
                File desJS3 = new File(exportDir + "/public_html/js/RecitationsBuilder.js");
                Files.copy(jsFile3.toPath(), desJS3.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
            }
            else if (fileName.equals("schedule.html") && sp.isUsed()){
                String jsonPath = baseAddr + "/OfficeHoursGridData.json";
                String jsonPath2 = baseAddr + "/RecitationsData.json";
                exportOfficeHoursJson(jsonPath, allData);
                exportRecitationJson(jsonPath2, allData);
                
                File indexHTML = new File(oldAddr + File.separator + "public_html" + File.separator + "/schedule.html");
                File desHTML = new File(exportDir + "/public_html/schedule.html");
                Files.copy(indexHTML.toPath(), desHTML.toPath());
                
                File jsFile1 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "HomeBuilder.js");
                File desJS1 = new File(exportDir + "/public_html/js/HomeBuilder.js");
                Files.copy(jsFile1.toPath(), desJS1.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                File jsFile2 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "ScheduleBuilder.js");
                File desJS2 = new File(exportDir + "/public_html/js/ScheduleBuilder.js");
                Files.copy(jsFile2.toPath(), desJS2.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
            }
            else if (fileName.equals("hws.html") && sp.isUsed()){
                String jsonPath = baseAddr + "/ScheduleData.json";
                exportScheduleJSON(jsonPath, allData);
                
                File indexHTML = new File(oldAddr + File.separator + "public_html" + File.separator + "/hws.html");
                File desHTML = new File(exportDir + "/public_html/hws.html");
                Files.copy(indexHTML.toPath(), desHTML.toPath());
                
                File jsFile1 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "HomeBuilder.js");
                File desJS1 = new File(exportDir + "/public_html/js/HomeBuilder.js");
                Files.copy(jsFile1.toPath(), desJS1.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                File jsFile2 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "HWsBuilder.js");
                File desJS2 = new File(exportDir + "/public_html/js/HWsBuilder.js");
                Files.copy(jsFile2.toPath(), desJS2.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
            }
            else if (fileName.equals("projects.html") && sp.isUsed()){
                String jsonPath = baseAddr + "/ProjectsData.json";
                exportProjectJson(jsonPath, allData);
                
                File indexHTML = new File(oldAddr + File.separator + "public_html" + File.separator + "/projects.html");
                File desHTML = new File(exportDir + "/public_html/projects.html");
                Files.copy(indexHTML.toPath(), desHTML.toPath());
                
                File jsFile1 = new File(oldAddr + File.separator + "public_html" + File.separator + "js" + File.separator + "ProjectsBuilder.js");
                File desJS1 = new File(exportDir + "/public_html/js/ProjectsBuilder.js");
                Files.copy(jsFile1.toPath(), desJS1.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
    
    public void exportSitePage(String path, AllData data) throws FileNotFoundException{
        CourseSiteData courseSiteData = data.getCourseSiteData();
        
        JsonObject courseSite = Json.createObjectBuilder()
                .add(JSON_SUBJECT, courseSiteData.getSubject())
                .add(JSON_NUMBER, courseSiteData.getNumber())
                .add(JSON_SEMESTER, courseSiteData.getSemester())
                .add(JSON_YEAR, courseSiteData.getYear())
                .add(JSON_TITLE, courseSiteData.getTitle())
                .add(JSON_INSTRUCTORNAME, courseSiteData.getInstructorName())
                .add(JSON_INSTRUCTORHOME, courseSiteData.getInstructorHome())
                .add(JSON_IMAGE1, courseSiteData.getFileName1())
                .add(JSON_IMAGE2, courseSiteData.getFileName2())
                .add(JSON_IMAGE3, courseSiteData.getFileName3())
                .add(JSON_STYLE_SHEET,courseSiteData.getStyleSheetName())
                .build();
       
                
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(courseSite);
	jsonWriter.close();
        
        try{
	           // INIT THE WRITER
            OutputStream os = new FileOutputStream(path);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(courseSite);
            String prettyPrinted = sw.toString();

            PrintWriter pw = new PrintWriter(path);

            pw.write(prettyPrinted);
            pw.close();       
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
                       
        
       
        
    }
    
    public void exportTeamAndStudent(String path, AllData data) throws FileNotFoundException{
        ProjectData projectData = data.getProjectData();
        ObservableList<Team> teamList = projectData.getTeam();
        JsonArrayBuilder TeamBuilder = Json.createArrayBuilder();
        for (Team team : teamList){
            String color1 = team.getColor().substring(2);
            int[] colorCode1 = new int[3];
            for (int i = 0; i < 3; i++){
                String singleColor = color1.substring(0,2);
                color1 = color1.substring(2);
                colorCode1[i] = Integer.parseInt(singleColor, 16);
                //System.out.println("color " + i + " " + colorCode[i]);
            }
            
            String color2 = team.getTextColor().substring(2);
            int[] colorCode2 = new int[3];
            for (int i = 0; i < 3; i++){
                String singleColor = color2.substring(0,2);
                color2 = color2.substring(2);
                colorCode2[i] = Integer.parseInt(singleColor, 16);   
            }
            
            JsonObject teamObject = Json.createObjectBuilder()
                    .add(TEAM_NAME, team.getTeamName())
                    .add(TEAM_RED, String.valueOf(colorCode1[0]))
                    .add(TEAM_GREEN, String.valueOf(colorCode1[1]))
                    .add(TEAM_BLUE, String.valueOf(colorCode1[2]))
                    .add(TEAM_TEXT_RED, String.valueOf(colorCode2[0]))
                    .add(TEAM_TEXT_GREEN, String.valueOf(colorCode2[1]))
                    .add(TEAM_TEXT_BLUE, String.valueOf(colorCode2[2]))
                    .build();
            TeamBuilder.add(teamObject);
        }
        JsonArray teamArray = TeamBuilder.build();
        
        ObservableList<Student> studentList = projectData.getStudent();
        JsonArrayBuilder StudentBuilder = Json.createArrayBuilder();
        for (Student s : studentList){
            JsonObject StudentObject = Json.createObjectBuilder()
                    .add(STUDENT_LASTNAME, s.getLastName())
                    .add(STUDENT_FIRSTNAME, s.getFirstName())
                    .add(STUDENT_TEAM, s.getTeam())
                    .add(STUDENT_ROLE, s.getRole())
                    .build();
            StudentBuilder.add(StudentObject);
        }
        JsonArray studentArray = StudentBuilder.build();
        
        JsonObject StudentTeamManagerJSO = Json.createObjectBuilder()
                .add(TEAM_TEAMS, teamArray)
                .add(STUDENT_STUDENTS, studentArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(StudentTeamManagerJSO);
	jsonWriter.close();
        
        try{
	           // INIT THE WRITER
            OutputStream os = new FileOutputStream(path);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(StudentTeamManagerJSO);
            String prettyPrinted = sw.toString();

            PrintWriter pw = new PrintWriter(path);

            pw.write(prettyPrinted);
            pw.close();       
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
                
        
    }
    
    public void exportRecitationJson(String path, AllData data) throws FileNotFoundException{
        RecitationData recitationData = data.getRecitationData();
        
        ObservableList<Recitation> recitationList = recitationData.getRecitation();
        JsonArrayBuilder RecitationBuilder = Json.createArrayBuilder();
        for (Recitation recitation : recitationList){
            JsonObject recitationObject = Json.createObjectBuilder()
                    .add(RECITATION_SECTION, recitation.getSection())
                    .add(RECITATION_DAY_TIME, recitation.getDay())
                    .add(RECITATION_LOCATION, recitation.getLocation())
                    .add(RECITATION_TA1, recitation.getTa1())
                    .add(RECITATION_TA2, recitation.getTa2())
                    .build();
            RecitationBuilder.add(recitationObject);
        } 
        JsonArray recitationArray = RecitationBuilder.build();
        
        JsonObject recitationObject = Json.createObjectBuilder()
                .add(RECITATION_RECITATION, recitationArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(recitationObject);
	jsonWriter.close();
        
        try{
	           // INIT THE WRITER
            OutputStream os = new FileOutputStream(path);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(recitationObject);
            String prettyPrinted = sw.toString();

            PrintWriter pw = new PrintWriter(path);

            pw.write(prettyPrinted);
            pw.close();       
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
        
    }
    
    public void exportOfficeHoursJson(String path, AllData data) throws FileNotFoundException{
        TAData taData = data.getTAData();
        String startHour = String.valueOf(taData.getStartHour());
        String endHour = String.valueOf(taData.getEndHour());
        
        ObservableList<TeachingAssistant> taList = taData.getTeachingAssistants();
        
        JsonArrayBuilder UndergradTABuilder = Json.createArrayBuilder();
        JsonArrayBuilder GradTABuilder = Json.createArrayBuilder();
        
        for (TeachingAssistant ta : taList){
            String name = ta.getName();
            String email = ta.getEmail();
            Boolean undergrad = ta.isUndergrad();
            if (undergrad){
                JsonObject UndergradTA = Json.createObjectBuilder()
                        .add(JSON_NAME, name)
                        .add(JSON_EMAIL, email)
                        .build();
                UndergradTABuilder.add(UndergradTA);
            }
            else{
                JsonObject GradTA = Json.createObjectBuilder()
                        .add(JSON_NAME, name)
                        .add(JSON_EMAIL, email)
                        .build();
                GradTABuilder.add(GradTA);
            }
            
            
        }
        JsonArray undergradTAArray = UndergradTABuilder.build();
        JsonArray gradTAArray = GradTABuilder.build();
        
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(taData);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        JsonObject officeHoursManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, startHour)
                .add(JSON_END_HOUR, endHour)
                .add(JSON_UNDERGRAD_TAS, undergradTAArray)
                .add(JSON_GRADUATE_TAS, gradTAArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(officeHoursManagerJSO);
	jsonWriter.close();
        
        try{
	           // INIT THE WRITER
            OutputStream os = new FileOutputStream(path);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(officeHoursManagerJSO);
            String prettyPrinted = sw.toString();

            PrintWriter pw = new PrintWriter(path);

            pw.write(prettyPrinted);
            pw.close();       
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
        
        
    }
    
    public void exportProjectJson(String path, AllData data) throws FileNotFoundException{
        ProjectData projectData = data.getProjectData();
        CourseSiteData courseSiteData = data.getCourseSiteData();
        
        String semester = courseSiteData.getSemester() + " " + courseSiteData.getYear();
        
        ObservableList<Team> teamList = projectData.getTeam();
        ObservableList<Student> student = projectData.getStudent();
        //ArrayList<String> teamName = new ArrayList();
        //for (Team t : teamList){
          //  teamName.add(t.getTeamName());
        //}
        
        JsonArrayBuilder ProjectBuilder = Json.createArrayBuilder();
        for (Team t : teamList){
            String tn = t.getTeamName();
            JsonArrayBuilder StudentBuilder = Json.createArrayBuilder();
            for (Student s : student){
                if (s.getTeam().equals(tn)){
                    String name = s.getFirstName() + " " + s.getLastName();
                    JsonObject StudentObject = Json.createObjectBuilder()
                            .add("", name)
                            .build();
                    StudentBuilder.add(name);
                }
                
            }
            JsonArray studentArray = StudentBuilder.build();
            
            JsonObject ProjectJson = Json.createObjectBuilder()
                    .add(PROJECT_NAME, tn)
                    .add(PROJECT_STUDENTS, studentArray)
                    .add(PROJECT_LINK, t.getLink())
                    .build();
            ProjectBuilder.add(ProjectJson);
           
        }
        JsonArray ProjectJsonArray = ProjectBuilder.build();
        
        JsonObject projectJsonObject = Json.createObjectBuilder()
                .add(PROJECT_SEMESTER, semester)
                .add(PROJECT_PROJECT, ProjectJsonArray)
                .build();
        
        JsonArrayBuilder noneSenseBuilder = Json.createArrayBuilder();
        noneSenseBuilder.add(projectJsonObject);
        
        JsonArray blankArray = noneSenseBuilder.build();
        
        
        JsonObject TeamManagerJSO = Json.createObjectBuilder()
                .add(PROJECT_WORK, blankArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(TeamManagerJSO);
	jsonWriter.close();
        
        try{
	           // INIT THE WRITER
            OutputStream os = new FileOutputStream(path);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(TeamManagerJSO);
            String prettyPrinted = sw.toString();

            PrintWriter pw = new PrintWriter(path);

            pw.write(prettyPrinted);
            pw.close();       
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
        
        
        //JsonArrayBuilder StudentBuilder = Json.createArrayBuilder();
        
        
    }
    
    public void exportScheduleJSON(String Path, AllData data) throws FileNotFoundException{
        ScheduleData scheduleData = data.getScheduleData();
        
        ObservableList<Schedule> scheduleList = scheduleData.getSchedule();
        
        JsonArrayBuilder HolidayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder LectureBuilder = Json.createArrayBuilder();
        JsonArrayBuilder ReferenceBuilder = Json.createArrayBuilder();
        JsonArrayBuilder RecitationBuilder = Json.createArrayBuilder();
        JsonArrayBuilder HWBuilder = Json.createArrayBuilder();
        for (Schedule sch : scheduleList){
            String date = sch.getDate();
            String[] dateList = date.split("/");
            String month = dateList[0];
            String day = dateList[1];
            if (sch.getType().equalsIgnoreCase("holiday")){
                JsonObject holidayJson = Json.createObjectBuilder()
                        .add(MONTH, month)
                        .add(DAY, day)
                        .add(TITLE, sch.getTitle())
                        .add(TOPIC, sch.getTopic())
                        .add(LINK, sch.getLink())
                        .add(CRITERIA, sch.getCriteria())
                        .build();
                HolidayBuilder.add(holidayJson);
            }
            else if (sch.getType().equalsIgnoreCase("lecture")){
                JsonObject lectureJson = Json.createObjectBuilder()
                        .add(MONTH, month)
                        .add(DAY, day)
                        .add(TITLE, sch.getTitle())
                        .add(TOPIC, sch.getTopic())
                        .add(LINK, sch.getLink())
                        .add(CRITERIA, sch.getCriteria())
                        .build();
                LectureBuilder.add(lectureJson);
            }
            else if (sch.getType().equalsIgnoreCase("reference")){
                JsonObject referenceJson = Json.createObjectBuilder()
                        .add(MONTH, month)
                        .add(DAY, day)
                        .add(TITLE, sch.getTitle())
                        .add(TOPIC, sch.getTopic())
                        .add(LINK, sch.getLink())
                        .add(CRITERIA, sch.getCriteria())
                        .build();
                ReferenceBuilder.add(referenceJson);
            }
            else if (sch.getType().equalsIgnoreCase("recitation")){
                JsonObject recitationJson = Json.createObjectBuilder()
                        .add(MONTH, month)
                        .add(DAY, day)
                        .add(TITLE, sch.getTitle())
                        .add(TOPIC, sch.getTopic())
                        .add(LINK, sch.getLink())
                        .add(CRITERIA, sch.getCriteria())
                        .build();
                RecitationBuilder.add(recitationJson);
            }
            else if (sch.getType().equalsIgnoreCase("hw")){
                JsonObject HWJson = Json.createObjectBuilder()
                        .add(MONTH, month)
                        .add(DAY, day)
                        .add(TITLE, sch.getTitle())
                        .add(TOPIC, sch.getTopic())
                        .add(LINK, sch.getLink())
                        .add(CRITERIA, sch.getCriteria())
                        .build();
                HWBuilder.add(HWJson);
            }
        }
        JsonArray holidayArray = HolidayBuilder.build();
        JsonArray lectureArray = LectureBuilder.build();
        JsonArray referenceArray = ReferenceBuilder.build();
        JsonArray recitationArray = RecitationBuilder.build();
        JsonArray HWArray = HWBuilder.build();
        
        String startDate = scheduleData.getStartDate();
        String[] startDateList = startDate.split("/");
        String startMonth = startDateList[0];
        String startDay = startDateList[1];
        
        String endDate = scheduleData.getEndDate();
        String[] endDateList = endDate.split("/");
        String endMonth = endDateList[0];
        String endDay = endDateList[1];
        
        JsonObject ScheduleManagerJSO = Json.createObjectBuilder()
                .add(SCHEDULE_STARTMONTH, startMonth)
                .add(SCHEDULE_STARTDAY, startDay)
                .add(SCHEDULE_ENDMONTH, endMonth)
                .add(SCHEDULE_ENDDAY, endDay)
                .add(SCHEDULE_HOLIDAYS, holidayArray)
                .add(SCHEDULE_LECTURE, lectureArray)
                .add(SCHEDULE_REFERENCE, referenceArray)
                .add(SCHEDULE_RECITATION, recitationArray)
                .add(SCHEDULE_HWS,HWArray)
                .build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(ScheduleManagerJSO);
	jsonWriter.close();
        
        try{
	           // INIT THE WRITER
            OutputStream os = new FileOutputStream(Path);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(ScheduleManagerJSO);
            String prettyPrinted = sw.toString();

            PrintWriter pw = new PrintWriter(Path);

            pw.write(prettyPrinted);
            pw.close();       
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
                
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
                    if (!targget.getName().contains(".html") && !targget.getName().endsWith(".js")){
                        FileInputStream input = new FileInputStream(targget);
                        FileOutputStream output = new FileOutputStream(newPath + "/" + (targget.getName()).toString());
                        byte[] b = new byte[1024 * 5];
                        int templength;
                        while ((templength = input.read(b)) != -1) {
                            output.write(b, 0, templength);
                        }
                        output.flush();
                        output.close();
                        input.close();
                    }
                } 
                if(targget.isDirectory())
                    copy(oldPath+"/"+file[i],newPath+"/"+file[i]); 
            } 
        } 
        catch (Exception e) {  
            e.printStackTrace(); 
        } 
    }

    
}