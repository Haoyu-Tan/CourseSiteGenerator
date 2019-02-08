package tam.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;

import java.awt.Image;
import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import tam.TAManagerApp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.print.PrintColor.COLOR;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import properties_manager.PropertiesManager;
import tam.TAManagerProp;
import static tam.TAManagerProp.ADD_BUTTON_TEXT;
import static tam.TAManagerProp.MISSING_TA_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TA_NAME_TITLE;
import static tam.TAManagerProp.UPDATE_BUTTON_TEXT;
import static tam.TAManagerProp.*;
import tam.style.TAStyle;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.file.TimeSlot;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.data.Recitation;
import tam.data.Schedule;
import tam.data.Student;
import tam.data.Team;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.util.Callback;
import javafx.scene.paint.Paint;
import tam.jtps.TAReplaceUR;
import tam.jtps.TAUndergradUR;
import tam.jtps.jTPS_Transaction;
import static tam.workspace.TAController.jTPS;
import javafx.scene.paint.Color;
import tam.data.ProjectData;
import tam.data.RecitationData;
import tam.data.ScheduleData;
import tam.data.SitePage;
import tam.jtps.CourseSiteCSSUR;
import tam.jtps.CourseSiteImage1UR;
import tam.jtps.CourseSiteNumberUR;
import tam.jtps.CourseSiteSemesterUR;
import tam.jtps.CourseSiteSubjectUR;
import tam.jtps.CourseSiteTitleUR;
import tam.jtps.CourseSiteYearUR;
import tam.jtps.ScheduleEndDateUR;
import tam.jtps.ScheduleStartDateUR;
/**
 * This class serves as the workspace component for the TA Manager
 * application. It provides all the user interface controls in 
 * the workspace area.
 * 
 * @author Richard McKenna
 */
public class TAWorkspace extends AppWorkspaceComponent {
    
    protected Button undoButton;
    protected Button redoButton;
    protected Button aboutButton;
    //Tab pane
    protected TabPane tabPane;
    protected Tab courseTab;
    protected Tab dataTab;
    protected Tab recitationTab;
    protected Tab scheduleTab;
    protected Tab projectTab;
    
    //basic components of course Tab Pane
    protected VBox courseBox;
    protected GridPane courseInfo;
    protected GridPane siteTemplate;
    protected GridPane pageStyle;
    
    //basic components of recitation tab pane
    protected VBox recitationPane;
    protected GridPane recitationInfo;
    protected HBox recitationTitlePane;
    
    //basic components of schedule tab pane
    protected VBox schedulePane;
    protected VBox calendarPane;
    protected HBox calendarComboPane;
    protected VBox scheduleItemPane;
    protected HBox scheduleTitlePane;
    protected GridPane scheduleEditPane;
    protected DatePicker scheduleStart;
    protected DatePicker scheduleEnd;
    protected DatePicker scheduleDate;
    
    //basic components of project tab pane
    protected VBox projectPane;
    protected VBox teamPane;
    protected HBox teamHeaderPane;
    protected GridPane editTeamPane;
    protected VBox studentPane;
    protected HBox studentHeaderPane;
    protected GridPane editStudentPane;
    
    
    
    //Button
    Button expButton;
    Button templateButton;
    Button imageButton1;
    Button imageButton2;
    Button imageButton3;
    Button removeButtonTA;
    Button removeButtonRecitation;
    Button removeButtonSchedule;
    Button addRecitation;
    Button clearRecitation;
    Button addSchedule;
    Button clearSchedule;
    Button removeTeam;
    Button addTeam;
    Button clearTeam;
    Button removeStudent;
    Button addStudent;
    Button clearStudent;
    
    //String
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    StringProperty exportDir = new SimpleStringProperty(props.getProperty(TAManagerProp.EXPORT_MSG.toString()));
    String templateDir = props.getProperty(TAManagerProp.SITE_MSG2.toString());
    String imagePath1 = "file:./images/drink.png";
    String imagePath2 = "file:./images/pizza.png";
    String imagePath3 = "file:./images/cake.png";
    
    //table column and table view
    TableView<SitePage> sitePageTable;
    //TableColumn<String, boolean> use; 
    TableColumn<SitePage, String> navbar;
    TableColumn<SitePage, String> fileName;
    TableColumn<SitePage, String> script;
    TableColumn<SitePage, Boolean> use;
    
    TableView<Recitation> recitationTable;
    TableColumn<Recitation, String> sectionCol;
    TableColumn<Recitation, String> instructorCol;
    TableColumn<Recitation, String> dayCol;
    TableColumn<Recitation, String> locationCol;
    TableColumn<Recitation, String> ta1Col;
    TableColumn<Recitation, String> ta2Col;
    
    TableView<Schedule> scheduleTable;
    TableColumn<Schedule, String> typeCol;
    TableColumn<Schedule, String> dateCol;
    TableColumn<Schedule, String> titleCol;
    TableColumn<Schedule, String> topicCol;
    
    TableView<Team> teamTable;
    TableColumn<Team, String> nameCol;
    TableColumn<Team, String> colorCol;
    TableColumn<Team, String> textCol;
    TableColumn<Team, String> linkCol;
    
    TableView<Student> studentTable;
    TableColumn<Student, String> firstNameCol;
    TableColumn<Student, String> lastNameCol;
    TableColumn<Student, String> studentTeamCol;
    TableColumn<Student, String> roleCol;
    
    //All choice box
    ObservableList<String> subjectB = FXCollections.observableArrayList(
            "CSE", "AMS", "PHY", "GEO"
    );
    ChoiceBox<String> subjectCombo = new ChoiceBox(subjectB);
    ObservableList<String> courseNum = FXCollections.observableArrayList(
            "219", "131", "133", "151", "161", "210", "301", "310",
            "114", "214", "220", "320", "101", "373", "308", "380",
            "215", "303", "300", "311", "102"
    );
    ChoiceBox<String> courseNumCombo = new ChoiceBox(courseNum);
    ObservableList<String> semester = FXCollections.observableArrayList(
           "Spring", "Summer", "Fall", "Winter"
    );
    ChoiceBox<String> semesterCombo = new ChoiceBox(semester);
    ObservableList<String> yearNum = FXCollections.observableArrayList(
           "2015", "2016", "2017", "2018", "2019", "2020", "2021",
            "2022", "2023", "2024"
    );
    ChoiceBox<String> yearCombo = new ChoiceBox(yearNum);
    
    //ObservableList<String> ss = FXCollections.observableArrayList(
      //      "sea_wolf.css", "style_sheet.css"
    //);
    ObservableList<String> ss;
    ChoiceBox<String> ssCombo;
    
    
    ChoiceBox<TeachingAssistant> rta1Combo;
    //ChoiceBox<String> rta1Combo = new ChoiceBox(rta1);
    
    
    ChoiceBox<TeachingAssistant> rta2Combo;
    ObservableList<String> scheduleType = FXCollections.observableArrayList(
            "holiday", "lecture", "hw"
    );
    ChoiceBox<String> scheduleTypeCombo = new ChoiceBox(scheduleType);
    
    //ObservableList<String> teamNameList = FXCollections.observableArrayList(
      //      "C4 Comics"
    //);
    ChoiceBox<Team> teamNameCombo;
    
    
    BorderPane myPane;
    HBox toolPane2;
    
    HBox taTitle;
    VBox TAPane;
    VBox rightMostPane;
    
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    TAManagerApp app;
    boolean add;
    boolean recitationAdd;
    boolean scheduleAdd;
    boolean teamAdd;
    boolean studentAdd;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    TAController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    TableColumn<TeachingAssistant, Boolean> undergradColumn;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button clearButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    
    ObservableList<String> time_options;
    ComboBox comboBox1;
    ComboBox comboBox2;
    
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;

    
    ObservableList<String> startTime = FXCollections.observableArrayList(
            "0:00am", "1:00am", "2:00am", "3:00am",
            "4:00am", "5:00am", "6:00am",
            "7:00am", "8:00am", "9:00am", "10:00am",
            "11:00am", "12:00pm", "1:00pm", 
            "2:00pm", "3:00pm",  "4:00pm", "5:00pm", 
            "6:00pm",  "7:00pm", "8:00pm", "9:00pm", 
            "10:00pm",  "11:00pm"
    );
    
    ObservableList<String> endTime = FXCollections.observableArrayList(
            "0:00am", "1:00am", "2:00am", "3:00am",
            "4:00am", "5:00am", "6:00am",
            "7:00am", "8:00am", "9:00am", "10:00am",
            "11:00am", "12:00pm", "1:00pm", 
            "2:00pm", "3:00pm",  "4:00pm", "5:00pm", 
            "6:00pm",  "7:00pm", "8:00pm", "9:00pm", 
            "10:00pm",  "11:00pm"
    );
    
    //Label
    Label infor;
    Label subjectLabel;
    Label numLabel;
    Label semesterLabel;
    Label yearLabel;
    Label titleLabel;
    
    Label instructorNmaeLabel;
    Label instructorHomeLabel;
    Label exportLabel;
    Label exportD;
    Label siteTemplateLabel;
    Label siteTemplateMsg;
    Label templateD;
    Label sitePage;
    Label pageStyleLabel;
    Label imageLabel1;
    Label imageLabel2;
    Label imageLabel3;
    Label styleSheetLabel;
    Label pageStyleMsg;
    Label recitationLabel;
    Label recitationLabel2;
    Label sectionLabel;
    Label instructorLabel;
    Label rdayLabel;
    Label rlocationLabel;
    Label rta1Label;
    Label rta2Label;
    Label scheduleLabel;
    Label calendarLabel;
    Label startDayLabel;
    Label endDayLabel;
    Label scheduleItemLabel;
    Label addScheduleLabel;
    Label stype;
    Label sdate;
    Label stime;
    Label stitle;
    Label stopic;
    Label slink;
    Label scriteria;
    Label projectLabel;
    Label teamLabel;
    Label addTeamLabel;
    Label teamName;
    Label teamColor;
    Label teamText;
    Label teamLink;
    Label studentLabel;
    Label addStudentLaebl;
    Label firstNameLabel;
    Label lastNameLabel;
    Label studentTeam;
    Label roleLabel;
    Label cssFile;
    
    //TextField
    TextField titleText;
    TextField instructorNmaeText;
    TextField instructorHomeText;
    TextField rsectionText;
    TextField rinstructorText;
    TextField rDayText;
    TextField rlocText;
    TextField timeText;
    TextField TitleText;
    TextField topicText;
    TextField linkText;
    TextField criteriaText;
    TextField studentNameText;
    TextField studentLinkText;
    TextField firstNameText;
    TextField lastNameText;
    TextField roleText;
    
    final ColorPicker colorPicker1;
    final Circle circle1;
    final ColorPicker colorPicker2;
    final Circle circle2;
    
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    boolean firstImage1;
    boolean firstImage2;
    boolean firstImage3;
    boolean removeRecitation = true;
    boolean isRemoveTeam = true;
    boolean isRemoveStudent = true;
    boolean isRemoveSchedule = true;
    
    /**
     * The contstructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public TAWorkspace(TAManagerApp initApp) {
        
        final KeyCombination keyCombCtrZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        final KeyCombination keyCombCtrY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
    
        // KEEP THIS FOR LATER
        app = initApp;
        add = true;
        recitationAdd = true;
        scheduleAdd = true;
        studentAdd = true;
        teamAdd = true;
        
        CourseSiteData courseSiteData = ((AllData) app.getDataComponent()).getCourseSiteData();
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //--------------------------------------
        myPane = new BorderPane();
        workspace = new BorderPane();
        tabPane = new TabPane();
        
        //initialize the basic pane for course detail pane
        courseBox = new VBox();
        courseInfo = new GridPane();
        siteTemplate = new GridPane();
        pageStyle = new GridPane();
        courseBox.getChildren().addAll(courseInfo, siteTemplate, pageStyle);
        
        //Button of the title
        toolPane2 = new HBox();
        
        
        undoButton = this.initChildButton(toolPane2, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), false);
        redoButton = this.initChildButton(toolPane2, REDO_ICON.toString(), REDO_TOOLTIP.toString(), false);
        aboutButton = this.initChildButton(toolPane2, ABOUT_ICON.toString(), ABOUT_TOOLTIP.toString(), false);
        toolPane2.setPadding(new Insets(0, 10, 0, 10));
        //undoButton.setGraphic(new ImageView(new Image("file:./images/Undo.png")));
        myPane.getChildren().add(toolPane2);
        app.getGUI().getFileToolbarPane().getChildren().add(myPane);
        //((BorderPane) workspace).setLeft(myPane);
        
        //--------------------- 
        //add components to course infor pane
        infor = new Label(props.getProperty(TAManagerProp.COURSE_INFO.toString()));
        //infor.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        infor.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(infor, 0, 0);
        
        subjectLabel = new Label(props.getProperty(TAManagerProp.SUBJECT.toString()));
        subjectLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        subjectLabel.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(subjectLabel, 0, 1);
        
        //subjectCombo.setPadding(new Insets(0, 0, 5, 10));
        
        subjectCombo.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
             
                if (subjectCombo.getValue() != null && !CourseSiteSubjectUR.isUndo){
                    
                    controller.handleSelectSubject();
                }
                else{
                    CourseSiteSubjectUR.isUndo = false;
                    //CourseSiteCSSUR.isChanged = false;
                }
                
            }
        });
        
        courseInfo.add(subjectCombo, 1, 1);
        
        numLabel = new Label(props.getProperty(TAManagerProp.NUMBER.toString()));
        numLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        numLabel.setPadding(new Insets(0, 5, 3, 10));
        courseInfo.add(numLabel, 2, 1);
        
        courseNumCombo.setPadding(new Insets(0, 0, 5, 10));
        
        courseNumCombo.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
             
                if (courseNumCombo.getValue() != null && !CourseSiteNumberUR.isUndo){
                    
                    controller.handleSelectNumber();
                }
                else{
                    CourseSiteNumberUR.isUndo = false;
                    //CourseSiteCSSUR.isChanged = false;
                }
                
            }
        });
        
        courseInfo.add(courseNumCombo, 3,1);
        
        semesterLabel = new Label(props.getProperty(TAManagerProp.SEMESTER.toString()));
        semesterLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        semesterLabel.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(semesterLabel, 0, 2);
        
        
        semesterCombo.setPadding(new Insets(0, 0, 5, 10));
        
        semesterCombo.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
             
                if (semesterCombo.getValue() != null && !CourseSiteSemesterUR.isUndo){
                    
                    controller.handleSelectSemester();
                }
                else{
                    CourseSiteSemesterUR.isUndo = false;
                    //CourseSiteCSSUR.isChanged = false;
                }
                
            }
        });
        
        
        courseInfo.add(semesterCombo, 1, 2);
        
        yearLabel = new Label(props.getProperty(TAManagerProp.YEAR.toString()));
        yearLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        yearLabel.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(yearLabel, 2, 2);
        
        //yearCombo.setPadding(new Insets(0, 0, 5, 10));
        
        yearCombo.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
             
                if (yearCombo.getValue() != null && !CourseSiteYearUR.isUndo){
                    
                    controller.handleSelectYear();
                }
                else{
                    CourseSiteYearUR.isUndo = false;
                    //CourseSiteCSSUR.isChanged = false;
                }
                
            }
        });
        
        courseInfo.add(yearCombo, 3, 2);
        
        titleLabel = new Label(props.getProperty(TAManagerProp.TITLE.toString()));
        titleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        titleLabel.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(titleLabel, 0, 3);
        
        titleText = new TextField();
        titleText.setPromptText("Computer Science III");
        titleText.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(titleText, 1, 3);
        
        instructorNmaeLabel = new Label(props.getProperty(TAManagerProp.INSTRUCTOR_NAME.toString()));
        instructorNmaeLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        instructorNmaeLabel.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(instructorNmaeLabel, 0, 4);
        
        instructorNmaeText = new TextField();
        instructorNmaeText.setPromptText("Richard McKenna");
        instructorNmaeText.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(instructorNmaeText, 1, 4);
        
        instructorHomeLabel = new Label(props.getProperty(TAManagerProp.INSTRUCTOR_HOME.toString()));
        instructorHomeLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        instructorHomeLabel.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(instructorHomeLabel, 0, 5);
        
        instructorHomeText = new TextField();
        instructorHomeText.setPromptText("http://www.cs.stonyrbrook.edu/~richard");
        instructorHomeText.setPrefWidth(350);
        instructorHomeText.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(instructorHomeText, 1, 5);
        
        exportLabel = new Label(props.getProperty(TAManagerProp.EXPORT_DIR.toString()));
        exportLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 16));
        exportLabel.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(exportLabel, 0, 6);
        
        exportD = new Label(exportDir.get());
        exportD.setFont(Font.font("Vedana", FontWeight.BOLD, 10));
        exportD.setPadding(new Insets(0, 0, 3, 10));
        courseInfo.add(exportD, 1, 6);
        

        expButton = new Button(props.getProperty(TAManagerProp.CHANGE_B.toString()));
        expButton.setPadding(new Insets(0, 0, 3, 10));
        expButton.setPrefWidth(65);
        courseInfo.add(expButton, 2, 6);
        
        courseInfo.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
        
        //---------------------------
        
        //------site template--------
        siteTemplateLabel = new Label(props.getProperty(TAManagerProp.SITE_TEMPLATE.toString()));
        siteTemplateLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        siteTemplateLabel.setPadding(new Insets(0, 0, 3, 10));
        siteTemplate.add(siteTemplateLabel, 0, 0);
        
        siteTemplateMsg = new Label(props.getProperty(TAManagerProp.SITE_MSG1.toString()));
        siteTemplateMsg.setFont(Font.font("Vedana", FontWeight.LIGHT, 13));
        siteTemplateMsg.setPadding(new Insets(0, 0, 3, 10));
        siteTemplate.add(siteTemplateMsg, 0, 1);
        
        templateD = new Label(templateDir);
        templateD.setFont(Font.font("Vedana", FontWeight.BOLD, 10));
        templateD.setPadding(new Insets(0, 0, 3, 10));
        siteTemplate.add(templateD, 0, 2);
        
        templateButton = new Button(props.getProperty(TAManagerProp.TEMPLATE_B.toString()));
        templateButton.setPadding(new Insets(0, 0, 3, 10));
        templateButton.setPrefWidth(250);
        siteTemplate.add(templateButton, 0, 3);
        
        sitePage = new Label(props.getProperty(TAManagerProp.SITE_PAGE.toString()));
        sitePage.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        sitePage.setPadding(new Insets(0, 0, 3, 10));
        siteTemplate.add(sitePage, 0, 4);
        
        sitePageTable = new TableView();
        sitePageTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        ObservableList<SitePage> siteData = courseSiteData.getSitePage(); 
        sitePageTable.setItems(siteData);
        navbar = new TableColumn(props.getProperty(TAManagerProp.NAV_BAR.toString()));
        fileName = new TableColumn(props.getProperty(TAManagerProp.FILE_NAME.toString()));
        script = new TableColumn(props.getProperty(TAManagerProp.SCRIPT.toString()));
        use = new TableColumn(props.getProperty(TAManagerProp.USE.toString()));
        navbar.setPrefWidth(250);
        fileName.setPrefWidth(250);
        script.setPrefWidth(250);
        use.setPrefWidth(100);
        
        use.setCellValueFactory(
        new Callback<CellDataFeatures<SitePage, Boolean>, ObservableValue<Boolean>>(){
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<SitePage, Boolean> param){
                CourseSiteData courseSiteData = ((AllData)app.getDataComponent()).getCourseSiteData();
                markWorkAsEdited();
                return param.getValue().useProperty();
            }
        }
        );
        use.setCellFactory(CheckBoxTableCell.forTableColumn(use));
        navbar.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("navbar")
        );
        fileName.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("fileName")
        );
        script.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("script")
        );
        sitePageTable.getColumns().add(use);
        sitePageTable.getColumns().add(navbar);
        sitePageTable.getColumns().add(fileName);
        sitePageTable.getColumns().add(script);
        sitePageTable.setEditable(true);
        siteTemplate.add(sitePageTable, 0, 5);
        
        //sitePageTable.setOnMouseClicked(e ->{
            
        //});
        
        siteTemplate.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
        //---------------------------
        
        //--------page style---------
        pageStyleLabel = new Label(props.getProperty(TAManagerProp.PAGE_STYLE.toString()));
        pageStyleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        pageStyleLabel.setPadding(new Insets(0, 0, 3, 10));
        pageStyle.add(pageStyleLabel, 0, 0);
        
        imageLabel1 = new Label(props.getProperty(TAManagerProp.IMAGE_MSG1.toString()));
        imageLabel1.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        imageLabel1.setPadding(new Insets(0, 0, 3, 10));
        pageStyle.add(imageLabel1, 0, 1);
        
        imageButton1 = new Button(props.getProperty(TAManagerProp.CHANGE_B.toString()));
        imageButton1.setPadding(new Insets(0, 0, 3, 10));
        imageButton1.setPrefWidth(65);
        pageStyle.add(imageButton1, 2, 1);
        
        imageLabel2 = new Label(props.getProperty(TAManagerProp.IMAGE_MSG2.toString()));
        imageLabel2.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        imageLabel2.setPadding(new Insets(0, 0, 3, 10));
        pageStyle.add(imageLabel2, 0, 2);
        
        imageButton2 = new Button(props.getProperty(TAManagerProp.CHANGE_B.toString()));
        imageButton2.setPadding(new Insets(0, 0, 3, 10));
        imageButton2.setPrefWidth(65);
        pageStyle.add(imageButton2, 2, 2);
        
        imageLabel3 = new Label(props.getProperty(TAManagerProp.IMAGE_MSG3.toString()));
        imageLabel3.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        imageLabel3.setPadding(new Insets(0, 0, 3, 10));
        pageStyle.add(imageLabel3, 0, 3);
        
        imageButton3 = new Button(props.getProperty(TAManagerProp.CHANGE_B.toString()));
        imageButton3.setPadding(new Insets(0, 0, 3, 10));
        imageButton3.setPrefWidth(65);
        pageStyle.add(imageButton3, 2, 3);
        
        javafx.scene.image.Image image1 = new javafx.scene.image.Image(imagePath1);
        firstImage1 = true;
        
        iv1 = new ImageView(image1);
        
        pageStyle.add(iv1, 1, 1);
        
        javafx.scene.image.Image image2 = new javafx.scene.image.Image(imagePath2);
        firstImage2 = true;
        //Image image2 = new Image(imagePath2);
        iv2 = new ImageView(image2);
        pageStyle.add(iv2, 1, 2);
        
        javafx.scene.image.Image image3 = new javafx.scene.image.Image(imagePath3);
        firstImage3 = true;
        //Image image3 = new Image(imagePath3);
        iv3 = new ImageView(image3);
        pageStyle.add(iv3, 1, 3);
        
        styleSheetLabel = new Label(props.getProperty(TAManagerProp.STYLE_SHEET.toString()));
        styleSheetLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        styleSheetLabel.setPadding(new Insets(0, 2, 3, 2));
        pageStyle.add(styleSheetLabel, 0, 4);
        
        
        courseSiteData.loadCSS();
        ss = courseSiteData.getCSSFile();
        ssCombo = new ChoiceBox();
        
        ssCombo.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
             
                if (ssCombo.getValue() != null && !CourseSiteCSSUR.isUndo){
                    
                    controller.handleSelectCSS();
                }
                else{
                    CourseSiteCSSUR.isUndo = false;
                    //CourseSiteCSSUR.isChanged = false;
                }
                
            }
        });
        
        ssCombo.setItems(ss);
        
        pageStyle.add(ssCombo, 2, 4);
        
        pageStyleMsg = new Label(props.getProperty(TAManagerProp.PAGE_MSG.toString()));
        pageStyleMsg.setFont(Font.font("Vedana", FontWeight.LIGHT, 13));
        pageStyleMsg.setPadding(new Insets(0, 2, 3, 2));
        pageStyle.add(pageStyleMsg, 0, 5);
        
        cssFile = new Label(props.getProperty(TAManagerProp.CSS_MSG.toString()));
        cssFile.setFont(Font.font("Vedana", FontWeight.LIGHT, 13));
        cssFile.setPadding(new Insets(0, 2, 3, 2));
        pageStyle.add(cssFile, 1, 4);
        
        pageStyle.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
        
        //----------------------end of course detail tab----------------------------------------
        
        /**
        //----------TADATA PANE-----------
        TAPane = new VBox();
        taTitle = new HBox();
        taTitle.setSpacing(375);
        
        HBox box1 = new HBox();
        HBox box2 = new HBox();
        box1.setSpacing(15);
        box2.setSpacing(30);
        
        HBox box3 = new HBox();
        HBox box4 = new HBox();
        
        Label TALabel = new Label("Teaching Assistants");
        TALabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        TALabel.setPadding(new Insets(0, 0, 3, 10));
        
        Label OfficeHourLabel = new Label("Office Hours");
        OfficeHourLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        OfficeHourLabel.setPadding(new Insets(0, 0, 3, 10));
        
        Label startOfficeHourLabel = new Label("Start: ");
        startOfficeHourLabel.setFont(Font.font("Vedana", FontWeight.LIGHT, 13));
        startOfficeHourLabel.setPadding(new Insets(0, 0, 3, 10));
        
        Label endOfficeHourLabel = new Label("End: ");
        endOfficeHourLabel.setFont(Font.font("Vedana", FontWeight.LIGHT, 13));
        endOfficeHourLabel.setPadding(new Insets(0, 0, 3, 10));
        
        removeButtonTA = new Button("-");
        removeButtonTA.setPadding(new Insets(0, 0, 3, 10));
        removeButtonTA.setPrefWidth(50);
        
        box3.getChildren().addAll(startOfficeHourLabel, startTimeCombo);
        box4.getChildren().addAll(endOfficeHourLabel, endTimeCombo);
        box1.getChildren().addAll(TALabel, removeButtonTA);
        box2.getChildren().addAll(OfficeHourLabel, box3, box4);
        
        taTitle.getChildren().addAll(box1, box2);
        taTitle.setStyle("-fx-background-color: aliceblue;");
        TAPane.getChildren().add(taTitle);
        **/
        
        
        //set 5 tab one the tab pane
        courseTab = new Tab(props.getProperty(TAManagerProp.COURSE_DETAIL.toString()));
        dataTab = new Tab(props.getProperty(TAManagerProp.TADATA.toString()));
        recitationTab = new Tab(props.getProperty(TAManagerProp.RECITATION_DATA.toString()));
        scheduleTab = new Tab(props.getProperty(TAManagerProp.SCHEDULE_DATA.toString()));
        projectTab = new Tab(props.getProperty(TAManagerProp.PROJECT_DATA.toString()));
        
        //add every content to tab pane
        courseTab.setContent(courseBox);
        //--------------------------------------
         
        
        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(TAManagerProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        
        removeButtonTA = new Button("-");
        //removeButtonTA.setPadding(new Insets(0, 0, 3, 10));
        removeButtonTA.setPrefWidth(50);
        
        tasHeaderBox.getChildren().addAll(tasHeaderLabel, removeButtonTA);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = ((AllData) app.getDataComponent()).getTAData();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(TAManagerProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(TAManagerProp.EMAIL_COLUMN_TEXT.toString());
        String undergradColumnText = props.getProperty(TAManagerProp.UNDERGRAD_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        undergradColumn = new TableColumn(undergradColumnText);
        nameColumn.setPrefWidth(150);
        emailColumn.setPrefWidth(350);
        undergradColumn.setPrefWidth(200);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        undergradColumn.setCellValueFactory(
        new Callback<CellDataFeatures<TeachingAssistant, Boolean>, ObservableValue<Boolean>>(){
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<TeachingAssistant, Boolean> param){
                //jTPS_Transaction undergradTAUR = new TAUndergradUR(param.getValue());
                //jTPS.addTransaction(undergradTAUR);
                System.out.println(param.getValue().undergradProperty());
                return param.getValue().undergradProperty();
            }
        }
        );
        
        
        
        undergradColumn.setCellFactory(CheckBoxTableCell.forTableColumn(undergradColumn));
        
        taTable.getColumns().add(undergradColumn);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        taTable.setEditable(true);
        
        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(TAManagerProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(TAManagerProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(TAManagerProp.CLEAR_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        
        
        
        //addButton.setMinWidth(80.0);
        clearButton = new Button(clearButtonText);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.3));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.3));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.25));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.15));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(TAManagerProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();

        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        
        /**
        time_options = FXCollections.observableArrayList(
        props.getProperty(TAManagerProp.TIME_12AM.toString()),
        props.getProperty(TAManagerProp.TIME_1AM.toString()),
        props.getProperty(TAManagerProp.TIME_2AM.toString()),
        props.getProperty(TAManagerProp.TIME_3AM.toString()),
        props.getProperty(TAManagerProp.TIME_4AM.toString()),
        props.getProperty(TAManagerProp.TIME_5AM.toString()),
        props.getProperty(TAManagerProp.TIME_6AM.toString()),
        props.getProperty(TAManagerProp.TIME_7AM.toString()),
        props.getProperty(TAManagerProp.TIME_8AM.toString()),
        props.getProperty(TAManagerProp.TIME_9AM.toString()),
        props.getProperty(TAManagerProp.TIME_10AM.toString()),
        props.getProperty(TAManagerProp.TIME_11AM.toString()),
        props.getProperty(TAManagerProp.TIME_12PM.toString()),
        props.getProperty(TAManagerProp.TIME_1PM.toString()),
        props.getProperty(TAManagerProp.TIME_2PM.toString()),
        props.getProperty(TAManagerProp.TIME_3PM.toString()),
        props.getProperty(TAManagerProp.TIME_4PM.toString()),
        props.getProperty(TAManagerProp.TIME_5PM.toString()),
        props.getProperty(TAManagerProp.TIME_6PM.toString()),
        props.getProperty(TAManagerProp.TIME_7PM.toString()),
        props.getProperty(TAManagerProp.TIME_8PM.toString()),
        props.getProperty(TAManagerProp.TIME_9PM.toString()),
        props.getProperty(TAManagerProp.TIME_10PM.toString()),
        props.getProperty(TAManagerProp.TIME_11PM.toString())
        ); **/
        comboBox1 = new ComboBox(startTime);
        comboBox2 = new ComboBox(endTime);
        
        
        
        Label startTimeLabel = new Label(props.getProperty(TAManagerProp.START_TIME.toString()));
        Label endTimeLabel = new Label(props.getProperty(TAManagerProp.END_TIME.toString()));
        
        startTimeLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        endTimeLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        
        officeHoursHeaderBox.getChildren().addAll(startTimeLabel, comboBox1, endTimeLabel);
        comboBox1.setPrefHeight(42);
        comboBox1.setPrefWidth(150);
        comboBox1.getSelectionModel().select(data.getStartHour());
        comboBox1.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(comboBox1.getSelectionModel().getSelectedIndex() != data.getStartHour())
                        controller.changeTime();
            }
        });
        officeHoursHeaderBox.getChildren().add(comboBox2);
        comboBox2.setPrefHeight(42);
        comboBox2.setPrefWidth(150);
        comboBox2.getSelectionModel().select(data.getEndHour());
        comboBox2.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(comboBox2.getSelectionModel().getSelectedIndex() != data.getEndHour())
                        controller.changeTime();
            }    
        });
        rightPane.getChildren().add(officeHoursGridPane);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane));
        //workspace = new BorderPane();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
        

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
       
        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));

        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new TAController(app);

        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        clearButton.setOnAction(e -> {
            
            addButton.setText(props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString()));
            add = true;
            nameTextField.clear();
            emailTextField.clear();
            taTable.getSelectionModel().select(null);
        });
        
        removeButtonTA.setOnMouseClicked(e ->{
            controller.handleKeyPress2();
        });
        
        taTable.setFocusTraversable(true);
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
        });
        taTable.setOnMouseClicked(e -> {
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
            addButton.setText(props.getProperty(TAManagerProp.UPDATE_BUTTON_TEXT.toString()));
            add = false;
            controller.loadTAtotext();
            //taTable.getSelectionModel().clearSelection();
            }
        });
        
        
        
        dataTab.setContent(sPane);
        
        //workspace.setOnKeyPressed(e ->{
          //  if(e.isControlDown())
            //    if(e.getCode() == KeyCode.Z)
              //      controller.Undo();
                //else if(e.getCode() == KeyCode.Y)
                  //  controller.Redo();
        //});
        
        undoButton.setOnAction(e ->{
            
            controller.Undo();
        });
        
        redoButton.setOnAction(e ->{
            CourseSiteCSSUR.isChanged = true;
            CourseSiteSubjectUR.isRedo = true;
            CourseSiteNumberUR.isRedo = true;
            CourseSiteSemesterUR.isRedo = true;
            CourseSiteYearUR.isRedo = true;
            CourseSiteTitleUR.isRedo = true;
            ScheduleStartDateUR.isRedo = true;
            ScheduleEndDateUR.isRedo = true;
            controller.Redo();
            CourseSiteCSSUR.isChanged = false;
            CourseSiteSubjectUR.isRedo = false;
            CourseSiteNumberUR.isRedo = false;
            CourseSiteSemesterUR.isRedo = false;
            CourseSiteYearUR.isRedo = false;
            CourseSiteTitleUR.isRedo = false;
            ScheduleStartDateUR.isRedo = false;
            ScheduleEndDateUR.isRedo = false;
        });
        
        
        
        //----------------end tadata
        
        //-------------recitation components-----------------
        recitationLabel = new Label(props.getProperty(TAManagerProp.RECITATION.toString()));
        recitationLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 20));
        recitationLabel.setPadding(new Insets(0, 0, 3, 10));
        
        removeButtonRecitation = new Button("-");
        //removeButtonRecitation.setPadding(new Insets(0, 0, 3, 10));
        removeButtonRecitation.setPrefWidth(25);
        
        recitationTitlePane = new HBox();
        recitationTitlePane.setSpacing(15);
        recitationTitlePane.getChildren().addAll(recitationLabel, removeButtonRecitation);
        recitationPane = new VBox();
        recitationPane.getChildren().add(recitationTitlePane);
        recitationTable = new TableView();
        recitationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        RecitationData recitationData = ((AllData) app.getDataComponent()).getRecitationData();
        ObservableList<Recitation> recitationTableData = recitationData.getRecitation();
        
        recitationTable.setItems(recitationTableData);
        sectionCol = new TableColumn(props.getProperty(TAManagerProp.SECTIONT.toString()));
        instructorCol = new TableColumn(props.getProperty(TAManagerProp.INSTRUCTORT.toString()));
        dayCol = new TableColumn(props.getProperty(TAManagerProp.DAYT.toString()));
        locationCol = new TableColumn(props.getProperty(TAManagerProp.LOCATIONT.toString()));
        ta1Col = new TableColumn(props.getProperty(TAManagerProp.TAT.toString()));
        ta2Col = new TableColumn(props.getProperty(TAManagerProp.TAT.toString()));
        
        
        navbar.setPrefWidth(250);
        fileName.setPrefWidth(250);
        script.setPrefWidth(250);
        
        sectionCol.setPrefWidth(100);
        instructorCol.setPrefWidth(150);
        dayCol.setPrefWidth(250);
        locationCol.setPrefWidth(200);
        ta1Col.setPrefWidth(150);
        ta2Col.setPrefWidth(150);
        recitationTable.setPrefWidth(100);
        dayCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("day")
        );
        sectionCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("section")
        );
        instructorCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("instructor")
        );
        locationCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("location")
        );
        ta1Col.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("ta1")
        );
        ta2Col.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("ta2")
        );
        
        recitationTable.getColumns().addAll(sectionCol, instructorCol, dayCol, locationCol, ta1Col, ta2Col);
        //System.out.println(workspace.widthProperty().add(0.8));
        //recitationTable.prefWidthProperty().bind(addBox.widthProperty().multiply(.15));
        recitationPane.getChildren().add(recitationTable);
        
        recitationInfo = new GridPane();
        
        recitationInfo.setHgap(15);
        recitationInfo.setVgap(5);
        
        recitationLabel2 = new Label(props.getProperty(TAManagerProp.ADD_EDIT.toString()));
        recitationLabel2.setFont(Font.font("Vedana", FontWeight.BOLD, 17));
        recitationLabel2.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(recitationLabel2, 0, 0);
        
        sectionLabel = new Label(props.getProperty(TAManagerProp.SECTION.toString()));
        sectionLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        sectionLabel.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(sectionLabel, 0, 1);
        
        instructorLabel = new Label(props.getProperty(TAManagerProp.INSTRUCTOR.toString()));
        instructorLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        instructorLabel.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(instructorLabel, 0, 2);
        
        rdayLabel = new Label(props.getProperty(TAManagerProp.DAY.toString()));
        rdayLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rdayLabel.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(rdayLabel, 0, 3);
        
        rlocationLabel = new Label(props.getProperty(TAManagerProp.LOCATION.toString()));
        rlocationLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rlocationLabel.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(rlocationLabel, 0, 4);
        
        rta1Label = new Label(props.getProperty(TAManagerProp.SVTA.toString()));
        rta1Label.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rta1Label.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(rta1Label, 0, 5);
        
        rta2Label = new Label(props.getProperty(TAManagerProp.SVTA.toString()));
        rta2Label.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rta2Label.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(rta2Label, 0, 6);
        
        rsectionText = new TextField();
        rsectionText.setPromptText("R01");
        rsectionText.setPrefWidth(300);
        rsectionText.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(rsectionText, 1, 1);
        
        rinstructorText = new TextField();
        rinstructorText.setPromptText("McKenna");
        rinstructorText.setPrefWidth(300);
        rinstructorText.setPadding(new Insets(0, 0, 3, 10));
        
        
        
        recitationInfo.add(rinstructorText, 1, 2);
        
        rDayText = new TextField();
        //rDayText.setPromptText("R01");
        rDayText.setPrefWidth(300);
        rDayText.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(rDayText, 1, 3);
        
        rlocText = new TextField();
        //rlocText.setPromptText("R01");
        rlocText.setPrefWidth(300);
        rlocText.setPadding(new Insets(0, 0, 3, 10));
        recitationInfo.add(rlocText, 1, 4);
        
        
        rta1Combo = new ChoiceBox(data.getTeachingAssistants());
        rta2Combo = new ChoiceBox(data.getTeachingAssistants());
        
        recitationInfo.add(rta1Combo, 1, 5);
        recitationInfo.add(rta2Combo, 1, 6);
        
        addRecitation = new Button(props.getProperty(TAManagerProp.ADD_UPDATE.toString()));
        addRecitation.setPrefWidth(150);
        
        clearRecitation = new Button(props.getProperty(TAManagerProp.CLEAR_B.toString()));
        clearRecitation.setPrefWidth(150);
        
        HBox button1 = new HBox();
        button1.getChildren().addAll(addRecitation, clearRecitation);
        button1.setSpacing(30);
        
        recitationInfo.add(button1, 0, 7);
        
        recitationPane.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
        
        recitationPane.getChildren().add(recitationInfo);
        
        recitationTab.setContent(recitationPane);
        //recitationInfo = new GridPane();
        
        
        //---------------------------------------------------
        
        //---------------------schedule data--------------------
        
        
            schedulePane = new VBox();
            calendarPane = new VBox();
            calendarComboPane = new HBox();
            scheduleItemPane = new VBox();
            scheduleTitlePane = new HBox();
            scheduleEditPane = new GridPane();
            
             
            scheduleEditPane.setHgap(20);
            scheduleEditPane.setVgap(10);
            
            scheduleLabel = new Label(props.getProperty(TAManagerProp.SCHEDULE.toString()));
            scheduleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
            scheduleLabel.setPadding(new Insets(0, 0, 3, 10));
            schedulePane.getChildren().add(scheduleLabel);
            
            calendarLabel = new Label(props.getProperty(TAManagerProp.CALENDAR_BOUN.toString()));
            calendarLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            calendarLabel.setPadding(new Insets(0, 0, 3, 10));
            calendarPane.getChildren().add(calendarLabel);
            
            startDayLabel = new Label(props.getProperty(TAManagerProp.START_DATE.toString()));
            startDayLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
            startDayLabel.setPadding(new Insets(0, 0, 3, 10));
            scheduleStart = new DatePicker();
            calendarComboPane.getChildren().addAll(startDayLabel, scheduleStart);
            
            endDayLabel = new Label(props.getProperty(TAManagerProp.END_DATE.toString()));
            endDayLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
            endDayLabel.setPadding(new Insets(0, 0, 3, 10));
            scheduleEnd = new DatePicker();
            calendarComboPane.getChildren().addAll(endDayLabel, scheduleEnd);
            
            calendarPane.getChildren().add(calendarComboPane);
            
            
            scheduleItemLabel = new Label(props.getProperty(TAManagerProp.SCHEDULE_ITEMS.toString()));
            scheduleItemLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            scheduleItemLabel.setPadding(new Insets(0, 0, 3, 10));
            
            removeButtonSchedule = new Button("-");
            removeButtonSchedule.setPrefWidth(25);
            
            scheduleTitlePane.getChildren().addAll(scheduleItemLabel, removeButtonSchedule);
            
            scheduleTable = new TableView();
            scheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            ScheduleData scheduleData = ((AllData) app.getDataComponent()).getScheduleData();
            ObservableList<Schedule> scheduleTableData = scheduleData.getSchedule();
            scheduleTable.setItems(scheduleTableData);
            
            typeCol = new TableColumn(props.getProperty(TAManagerProp.TYPE.toString()));
            typeCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("type")
            );
            dateCol = new TableColumn(props.getProperty(TAManagerProp.DATE.toString()));
            dateCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("date")
            );
            titleCol = new TableColumn(props.getProperty(TAManagerProp.TITLE.toString()));
            titleCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("title")
            );
            topicCol = new TableColumn(props.getProperty(TAManagerProp.TOPIC.toString()));
            topicCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("topic")
            );
            
            typeCol.setPrefWidth(200);
            dateCol.setPrefWidth(200);
            titleCol.setPrefWidth(200);
            topicCol.setPrefWidth(350);
            
            scheduleTable.getColumns().addAll(typeCol, dateCol, titleCol, topicCol);
            
            scheduleItemPane.getChildren().addAll(scheduleTitlePane, scheduleTable);
            
            addScheduleLabel = new Label(props.getProperty(TAManagerProp.ADD_EDIT.toString()));
            addScheduleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 17));
            addScheduleLabel.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(addScheduleLabel, 0, 0);
            
            stype = new Label(props.getProperty(TAManagerProp.TYPE.toString()));
            stype.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            stype.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(stype, 0, 1);
            
            sdate = new Label(props.getProperty(TAManagerProp.DATE.toString()));
            sdate.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            sdate.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(sdate, 0, 2);
            
            stime = new Label(props.getProperty(TAManagerProp.TIME.toString()));
            stime.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            stime.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(stime, 0, 3);
            
            stitle = new Label(props.getProperty(TAManagerProp.TITLE.toString()));
            stitle.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            stitle.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(stitle, 0, 4);
            
            stopic = new Label(props.getProperty(TAManagerProp.TOPIC.toString()));
            stopic.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            stopic.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(stopic, 0, 5);
            
            slink = new Label(props.getProperty(TAManagerProp.LINK.toString()));
            slink.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            slink.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(slink, 0, 6);
            
            scriteria = new Label(props.getProperty(TAManagerProp.CRITERIA.toString()));
            scriteria.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
            scriteria.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(scriteria, 0, 7);
            
            addSchedule = new Button(props.getProperty(TAManagerProp.ADD_UPDATE.toString()));
            addSchedule.setPrefWidth(150);
            scheduleEditPane.add(addSchedule, 0, 8);
        
            clearSchedule = new Button(props.getProperty(TAManagerProp.CLEAR_B.toString()));
            clearSchedule.setPrefWidth(150);
            scheduleEditPane.add(clearSchedule, 1, 8);
            
            
            scheduleEditPane.add(scheduleTypeCombo, 1, 1);
            scheduleDate = new DatePicker();
            scheduleEditPane.add(scheduleDate, 1, 2);
            
            timeText = new TextField();
            
            timeText.setPrefWidth(300);
            timeText.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(timeText, 1, 3);
            
            TitleText = new TextField();
            
            TitleText.setPrefWidth(300);
            TitleText.setPadding(new Insets(0, 0, 3, 10));
            
            
            
            scheduleEditPane.add(TitleText, 1, 4);
            
            topicText = new TextField();
            
            topicText.setPrefWidth(300);
            topicText.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(topicText, 1, 5);
            
            linkText = new TextField();
            
            linkText.setPrefWidth(300);
            linkText.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(linkText, 1, 6);
            
            criteriaText = new TextField();
            
            criteriaText.setPrefWidth(300);
            criteriaText.setPadding(new Insets(0, 0, 3, 10));
            scheduleEditPane.add(criteriaText, 1, 7);
            
            
            scheduleItemPane.getChildren().add(scheduleEditPane);
            schedulePane.getChildren().addAll(calendarPane, scheduleItemPane);
            calendarPane.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
            scheduleItemPane.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
            schedulePane.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
            scheduleTab.setContent(schedulePane);
            
        //--------------------------end----------------------------
        
        //----------------project--------------
        
        projectPane = new VBox();
        teamPane = new VBox();
        teamHeaderPane = new HBox();
        editTeamPane = new GridPane();
        studentPane = new VBox();
        studentHeaderPane = new HBox();
        editStudentPane = new GridPane();
        
        editTeamPane.setHgap(15);
        editTeamPane.setVgap(5);
        
        editStudentPane.setHgap(15);
        editStudentPane.setVgap(5);
        
        projectLabel = new Label(props.getProperty(TAManagerProp.PROJECT.toString()));
        projectLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        projectLabel.setPadding(new Insets(0, 0, 3, 10));
        projectPane.getChildren().add(projectLabel);
        
        
        teamLabel = new Label(props.getProperty(TAManagerProp.TEAM.toString()));
        teamLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        teamLabel.setPadding(new Insets(0, 0, 3, 10));
        teamHeaderPane.getChildren().add(teamLabel);
        
        removeTeam = new Button("-");
        removeTeam.setPrefWidth(25);
        teamHeaderPane.getChildren().add(removeTeam);
        
        teamTable = new TableView();
        teamTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        ProjectData projectData = ((AllData) app.getDataComponent()).getProjectData();
        ObservableList<Team> teamTableData = projectData.getTeam();
        teamTable.setItems(teamTableData);
        
        nameCol = new TableColumn(props.getProperty(TAManagerProp.NAMET.toString()));
        colorCol = new TableColumn(props.getProperty(TAManagerProp.COLORT.toString()));
        textCol = new TableColumn(props.getProperty(TAManagerProp.TEXT_COLORT.toString()));
        linkCol = new TableColumn(props.getProperty(TAManagerProp.LINKT.toString()));
        
        nameCol.setPrefWidth(150);
        colorCol.setPrefWidth(150);
        textCol.setPrefWidth(250);
        linkCol.setPrefWidth(100);
        
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("teamName")
        );   
        colorCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("color")
        );
        textCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("textColor")
        );
        linkCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("link")
        );
        teamTable.getColumns().addAll(nameCol, colorCol, textCol, linkCol);
        teamPane.getChildren().addAll(teamHeaderPane, teamTable);
        
        
        colorPicker1 = new ColorPicker(Color.WHITE);
 
        circle1 = new Circle(50);
        circle1.setFill(colorPicker1.getValue());
 
        colorPicker1.setOnAction(e ->{
                circle1.setFill(colorPicker1.getValue());
            
        });
        
        colorPicker2 = new ColorPicker(Color.WHITE);
 
        circle2 = new Circle(50);
        circle2.setFill(colorPicker2.getValue());
 
        colorPicker2.setOnAction(e ->{
                circle2.setFill(colorPicker2.getValue());
            
        });
        
        HBox color2 = new HBox();
        color2.getChildren().addAll(circle2, colorPicker2);
        color2.setSpacing(30);
        
        HBox color1 = new HBox();
        color1.getChildren().addAll(circle1, colorPicker1);
        color1.setSpacing(30);
        
        //editTeamPane.setPadding(new Insets(10, 10, 10, 10));
        
        addTeamLabel = new Label(props.getProperty(TAManagerProp.ADD_EDIT.toString()));
        addTeamLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        addTeamLabel.setPadding(new Insets(0, 0, 3, 10));
        editTeamPane.add(addTeamLabel, 0, 0);
        
        editTeamPane.add(color1, 1, 2);
        //editTeamPane.add(colorPicker1, 2, 2);
        editTeamPane.add(color2, 3, 2);
        //editTeamPane.add(colorPicker2, 5, 2);
        
        
        teamName = new Label(props.getProperty(TAManagerProp.TEAM_NAME.toString()));
        teamName.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        teamName.setPadding(new Insets(0, 0, 3, 10));
        editTeamPane.add(teamName, 0, 1);
        
        teamColor = new Label(props.getProperty(TAManagerProp.COLOR.toString()));
        teamColor.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        teamColor.setPadding(new Insets(0, 0, 3, 10));
        editTeamPane.add(teamColor, 0, 2);
      
        teamText = new Label(props.getProperty(TAManagerProp.TEXT_COLOR.toString()));
        teamText.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        teamText.setPadding(new Insets(0, 0, 3, 10));
        editTeamPane.add(teamText, 2, 2);
        
        teamLink = new Label(props.getProperty(TAManagerProp.LINK.toString()));
        teamLink.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        teamLink.setPadding(new Insets(0, 0, 3, 10));
        editTeamPane.add(teamLink, 0, 3);
        
        addTeam = new Button(props.getProperty(TAManagerProp.ADD_UPDATE.toString()));
        addTeam.setPrefWidth(150);
        editTeamPane.add(addTeam, 0, 4);
        
        clearTeam = new Button(props.getProperty(TAManagerProp.CLEAR_B.toString()));
        clearTeam.setPrefWidth(150);
        editTeamPane.add(clearTeam, 1, 4);
        
        studentNameText = new TextField();
        studentNameText.setPrefWidth(300);
        studentNameText.setPadding(new Insets(0, 0, 3, 10));
        editTeamPane.add(studentNameText, 1, 1);
        
        studentLinkText = new TextField();
            
        studentLinkText.setPrefWidth(300);
        studentLinkText.setPadding(new Insets(0, 0, 3, 10));
        editTeamPane.add(studentLinkText, 1, 3);
        
        teamPane.getChildren().add(editTeamPane);
        
        studentLabel = new Label(props.getProperty(TAManagerProp.STUDENT.toString()));
        studentLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        studentLabel.setPadding(new Insets(0, 0, 3, 10));
        studentHeaderPane.getChildren().add(studentLabel);
        
        removeStudent = new Button("-");
        removeStudent.setPrefWidth(25);
        studentHeaderPane.getChildren().add(removeStudent);
        
        addStudentLaebl = new Label(props.getProperty(TAManagerProp.ADD_EDIT.toString()));
        addStudentLaebl.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        addStudentLaebl.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(addStudentLaebl, 0, 0);
        
        firstNameLabel = new Label(props.getProperty(TAManagerProp.FIRST_NAME.toString()));
        firstNameLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        firstNameLabel.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(firstNameLabel, 0, 1);
        
        lastNameLabel = new Label(props.getProperty(TAManagerProp.LAST_NAME.toString()));
        lastNameLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        lastNameLabel.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(lastNameLabel, 0, 2);
        
        studentTeam = new Label(props.getProperty(TAManagerProp.TEAM_NAME.toString()));
        studentTeam.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        studentTeam.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(studentTeam, 0, 3);
        
        roleLabel = new Label(props.getProperty(TAManagerProp.ROLE.toString()));
        roleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        roleLabel.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(roleLabel, 0, 4);
        
        firstNameText = new TextField();
        firstNameText.setPrefWidth(300);
        firstNameText.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(firstNameText, 1, 1);
        
        lastNameText = new TextField();
        lastNameText.setPrefWidth(300);
        lastNameText.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(lastNameText, 1, 2);
        
        roleText = new TextField();
        roleText.setPrefWidth(300);
        roleText.setPadding(new Insets(0, 0, 3, 10));
        editStudentPane.add(roleText, 1, 4);
        
        
        teamNameCombo = new ChoiceBox(teamTableData);
        
        editStudentPane.add(teamNameCombo, 1, 3);
        
        addStudent = new Button(props.getProperty(TAManagerProp.ADD_UPDATE.toString()));
        addStudent.setPrefWidth(150);
        
        clearStudent = new Button(props.getProperty(TAManagerProp.CLEAR_B.toString()));
        clearStudent.setPrefWidth(150);
        
        editStudentPane.add(addStudent, 0, 5);
        
       
        editStudentPane.add(clearStudent, 1, 5);
        
        studentTable = new TableView();
        studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Student> studentTableData = projectData.getStudent();
        studentTable.setItems(studentTableData);
        
        
        firstNameCol = new TableColumn(props.getProperty(TAManagerProp.FIRST_NAMET.toString()));
        lastNameCol = new TableColumn(props.getProperty(TAManagerProp.LAST_NAMET.toString()));
        studentTeamCol = new TableColumn(props.getProperty(TAManagerProp.TEAMT.toString()));
        roleCol = new TableColumn(props.getProperty(TAManagerProp.ROLET.toString()));
        
        firstNameCol.setPrefWidth(200);
        lastNameCol.setPrefWidth(200);
        studentTeamCol.setPrefWidth(200);
        roleCol.setPrefWidth(200);
        
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );   
        
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        
        studentTeamCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("team")
        );
        
        roleCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("role")
        );
        
        studentTable.getColumns().addAll(firstNameCol, lastNameCol, studentTeamCol, roleCol);
        
        studentPane.getChildren().addAll(studentHeaderPane, studentTable, editStudentPane);
        
        
        projectPane.getChildren().addAll(teamPane, studentPane);
        
        teamPane.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
        studentPane.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
        projectPane.setStyle("-fx-border-color: paleturquoise; -fx-background-color: aliceblue;");
        projectTab.setContent(projectPane);
        
        //----------------end------------------
        
        
        tabPane.getTabs().addAll(courseTab, dataTab, recitationTab, scheduleTab, projectTab);
        workspace.setStyle("-fx-background-color: lightblue;");
        ((BorderPane) workspace).setCenter(tabPane);
        //activateWorkspace(app.getGUI().getAppPane());
        //((BorderPane) workspace).setCenter(sPane);
        
        
        //Action Part
        
        //-------------------Course Details Pane Action-------------------------
        imageButton1.setOnAction(e ->{
            
            try {
                controller.handleImageButton1();
            } catch (URISyntaxException ex) {
                Logger.getLogger(TAWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        imageButton2.setOnAction(e ->{
            
            try {
                controller.handleImageButton2();
            } catch (URISyntaxException ex) {
                Logger.getLogger(TAWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        imageButton3.setOnAction(e ->{
            
            try {
                controller.handleImageButton3();
            } catch (URISyntaxException ex) {
                Logger.getLogger(TAWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        titleText.setOnAction(e ->{
               controller.handleTitleText(); 
        });
        
        instructorNmaeText.setOnAction(e ->{
            controller.handleIstructorName();
        });
        
        instructorHomeText.setOnAction(e ->{
            controller.handleInstructorHome();
        });
        
        expButton.setOnAction(e ->{
            controller.handleExportButton();
        });
        
        templateButton.setOnAction(e ->{
            controller.handleTemplateButton();
        });
        
        //ssCombo.setOnAction(e ->{
            
          //      controller.handleSelectCSS();
            
        //});
        
        
        
        
        
        //------------------------Recitation Pane Action------------------------
        addRecitation.setOnAction(e ->{
            
            if (recitationAdd) {
                String TA1 = rta1Combo.getValue().getName();
                String TA2 = rta2Combo.getValue().getName();

                controller.handleAddRecitation(TA1, TA2);
                
                rta1Combo.getSelectionModel().select(null);
                rta2Combo.getSelectionModel().select(null);
            }
            else{
                controller.handleUpdateRecitation();
                //recitationAdd = true;
                
            }
            
            
            
        });
        
        clearRecitation.setOnAction(e ->{
            rsectionText.clear();
            rinstructorText.clear();
            rlocText.clear();
            rDayText.clear();
            timeText.clear();
            rta1Combo.getSelectionModel().select(null);
            rta2Combo.getSelectionModel().select(null);
            recitationTable.getSelectionModel().clearSelection();
            recitationAdd = true;
            
        });
        
        removeButtonRecitation.setOnMouseClicked(e ->{
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(REMOVE_CONFIRM_TITLE), props.getProperty(REMOVE_RECITATION_MSG));

            // AND NOW GET THE USER'S SELECTION
            String selection = yesNoDialog.getSelection();
            if (selection.equals(AppYesNoCancelDialogSingleton.NO) || selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
                removeRecitation = false;
            }
            
            if (removeRecitation) {
                controller.handleDeleteRecitation();
            }
                recitationTable.getSelectionModel().clearSelection();
                rsectionText.clear();
                rinstructorText.clear();
                rlocText.clear();
                rDayText.clear();
                timeText.clear();
                rta1Combo.getSelectionModel().select(null);
                rta2Combo.getSelectionModel().select(null);
                recitationAdd = true;
                recitationTable.getSelectionModel().clearSelection();
                
        });
        
        recitationTable.setOnMouseClicked(e -> {
            Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
                recitationAdd = false;
                controller.loadRecitationtotext();
            }
        });
        
        //-------------------------Team and Student Pane Action-----------------------------
        
        
        
        studentTable.setOnMouseClicked(e ->{
            Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
                studentAdd = false;
                controller.loadStudenttoText();
            }
        });
        
        addStudent.setOnAction(e ->{
            if (studentAdd){
                controller.handleAddStudent();
            }
            else{
                controller.handleUpdateStudent();
            }
        });
        
        addTeam.setOnAction(e ->{
            if (teamAdd){ 
                controller.handleAddTeam();
            }
            else{
                controller.handleUpdateTeam();
                ObservableList<Student> s = projectData.getStudent();
                
                
            }
            
        });
        
        clearTeam.setOnAction(e ->{
            teamAdd = true;
            circle1.setFill(Color.WHITE);
            circle2.setFill(Color.WHITE);
            colorPicker1.setValue(null);
            colorPicker2.setValue(null);
            studentNameText.clear();
            studentLinkText.clear();
            teamTable.getSelectionModel().clearSelection();
            teamAdd = true;
        });
        
        
        teamTable.setOnMouseClicked(e ->{
            Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
                teamAdd = false;
                
                Team team = (Team)selectedItem;
                String teamName = team.getTeamName();
                String teamColor = team.getColor();
                String textColor = team.getTextColor();
                String link = team.getLink();
                
                Color c1 = Color.web(teamColor);
                colorPicker1.setValue(c1);
                
                Color c2 = Color.web(textColor);
                colorPicker2.setValue(c2);
                
                circle1.setFill(c1);
                circle2.setFill(c2);
                
                studentNameText.setText(teamName);
                studentLinkText.setText(link);
                
            }
        });
        
        removeTeam.setOnAction(e ->{
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(REMOVE_CONFIRM_TITLE), props.getProperty(REMOVE_TEAM_MSG));

            // AND NOW GET THE USER'S SELECTION
            String selection = yesNoDialog.getSelection();
            if (selection.equals(AppYesNoCancelDialogSingleton.NO) || selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
                isRemoveTeam = false;
            }
            
            if (isRemoveTeam){
                controller.handleTeamRemove();
            }
            
            studentNameText.clear();
            studentLinkText.clear();
            colorPicker1.setValue(null);
            colorPicker2.setValue(null);
            circle1.setFill(Color.WHITE);
            circle2.setFill(Color.WHITE);
            teamTable.getSelectionModel().clearSelection();
            
        });
        
        removeStudent.setOnAction(e ->{
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(REMOVE_CONFIRM_TITLE), props.getProperty(REMOVE_STUDENT_MSG));

            // AND NOW GET THE USER'S SELECTION
            String selection = yesNoDialog.getSelection();
            if (selection.equals(AppYesNoCancelDialogSingleton.NO) || selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
                isRemoveStudent = false;
            }
            if (isRemoveStudent){
                controller.handleRemoveStudent();
            }
            
            firstNameText.clear();
            lastNameText.clear();
            teamNameCombo.getSelectionModel().select(null);
            roleText.clear();
            studentTable.getSelectionModel().clearSelection();
            
        });
        
        
        
        
        //-----------------------Schedule Pane Action---------------------------
        
        clearSchedule.setOnAction(e ->{
            scheduleAdd = false;
            timeText.clear();
            TitleText.clear();
            topicText.clear();
            linkText.clear();
            criteriaText.clear();
            scheduleTypeCombo.getSelectionModel().select(null);
            scheduleDate.setValue(null);
            scheduleTable.getSelectionModel().clearSelection();
            scheduleAdd = true;
        });
        
        addSchedule.setOnAction(e ->{
            if (scheduleAdd){
                String scheduleType = scheduleTypeCombo.getValue();
                String selectedDate = scheduleDate.getValue().toString();

                controller.handleAddSchedule(scheduleType, selectedDate);
            }
            else{
                String scheduleType = scheduleTypeCombo.getValue();
                String selectedDate = scheduleDate.getValue().toString();
                controller.handleUpdateSchedule(scheduleType, selectedDate);
            }
        });
        
        
        scheduleTable.setOnMouseClicked(e ->{
            Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
                scheduleAdd = false;
                controller.loadScheduletotext();
            }
        });
        
        
        removeButtonSchedule.setOnAction(e ->{
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
            yesNoDialog.show(props.getProperty(REMOVE_CONFIRM_TITLE), props.getProperty(REMOVE_RECITATION_MSG));

            // AND NOW GET THE USER'S SELECTION
            String selection = yesNoDialog.getSelection();
            if (selection.equals(AppYesNoCancelDialogSingleton.NO) || selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
                isRemoveSchedule = false;
            }
            if (isRemoveSchedule){
                controller.handleRemoveSchedule();
            }
            scheduleAdd = false;
            timeText.clear();
            TitleText.clear();
            topicText.clear();
            linkText.clear();
            criteriaText.clear();
            scheduleTypeCombo.getSelectionModel().select(null);
            scheduleDate.setValue(null);
            scheduleTable.getSelectionModel().clearSelection();
            
        });
        
        
        
        
        
        scheduleStart.setOnAction(e ->{
            if (!ScheduleStartDateUR.isUndo && !ScheduleStartDateUR.isRedo) {
                if (scheduleStart.getValue() != null && scheduleEnd.getValue() != null) {
                    LocalDate startDate = scheduleStart.getValue();
                    LocalDate endDate = scheduleEnd.getValue();

                    if (scheduleData.isMonday(startDate) && scheduleData.isFriday(endDate)) {
                        controller.handleScheduleStart(startDate.toString(), endDate.toString());
                    } else {

                        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                        dialog.show(props.getProperty(NOT_MONDAY_OR_FRIDAY_TITLE), props.getProperty(NOT_MONDAY_OR_FRIDAY_TEXT));
                    }

                }
            }
            else{
                ScheduleStartDateUR.isUndo = false;
                ScheduleStartDateUR.isRedo = false;
            }
        });
        
        
        scheduleEnd.setOnAction(e ->{
            if (!ScheduleEndDateUR.isUndo && !ScheduleEndDateUR.isRedo){
                if (scheduleStart.getValue() != null && scheduleEnd.getValue() != null) {
                    LocalDate startDate = scheduleStart.getValue();
                    LocalDate endDate = scheduleEnd.getValue();

                    if (scheduleData.isMonday(startDate) && scheduleData.isFriday(endDate)) {
                        controller.handleScheduleEnd(startDate.toString(), endDate.toString());

                    } else {

                        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                        dialog.show(props.getProperty(NOT_MONDAY_OR_FRIDAY_TITLE), props.getProperty(NOT_MONDAY_OR_FRIDAY_TEXT));
                    }

                }
            }
            else{
                ScheduleEndDateUR.isUndo = false;
                ScheduleEndDateUR.isRedo = false;
            }
        });
        
        aboutButton.setOnAction(e ->{
            controller.handleAbout();
        });
        
        workspace.setOnKeyPressed(e ->{
            if (keyCombCtrZ.match(e)){
                //data.handleToggleUndo();
                //data.handleAddUndo();
                controller.Undo();
            }
            else if (keyCombCtrY.match(e)){
                //data.handleToggleRedo();
                //data.handleAddRedo();
                CourseSiteCSSUR.isChanged = true;
                CourseSiteSubjectUR.isRedo = true;
                CourseSiteNumberUR.isRedo = true;
                CourseSiteSemesterUR.isRedo = true;
                CourseSiteYearUR.isRedo = true;
                CourseSiteTitleUR.isRedo = true;
                ScheduleStartDateUR.isRedo = true;
                ScheduleEndDateUR.isRedo = true;
                controller.Redo();
                CourseSiteCSSUR.isChanged = false;
                CourseSiteSubjectUR.isRedo = false;
                CourseSiteNumberUR.isRedo = false;
                CourseSiteSemesterUR.isRedo = false;
                CourseSiteYearUR.isRedo = false;
                CourseSiteTitleUR.isRedo = false;
                ScheduleStartDateUR.isRedo = false;
                ScheduleEndDateUR.isRedo = false;
            }
        });
        
    }
    
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    
    public void setTextStyle(){
        infor.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        subjectLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        numLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        semesterLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        yearLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        titleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        instructorNmaeLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        instructorHomeLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        exportLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 16));
        exportD.setFont(Font.font("Vedana", FontWeight.BOLD, 10));
        siteTemplateLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        siteTemplateMsg.setFont(Font.font("Vedana", FontWeight.LIGHT, 13));
        templateD.setFont(Font.font("Vedana", FontWeight.BOLD, 10));
        sitePage.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        pageStyleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        imageLabel1.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        imageLabel2.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        imageLabel3.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        styleSheetLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        pageStyleMsg.setFont(Font.font("Vedana", FontWeight.LIGHT, 13));
        recitationLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 20));
        recitationLabel2.setFont(Font.font("Vedana", FontWeight.BOLD, 17));
        sectionLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        instructorLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rdayLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rlocationLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rta1Label.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        rta2Label.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        scheduleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        calendarLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        startDayLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        endDayLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        scheduleItemLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        addScheduleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 17));
        stype.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        sdate.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        stime.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        stitle.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        stopic.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        slink.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        scriteria.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        projectLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 18));
        teamLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        addTeamLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        teamName.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        teamColor.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        teamText.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        teamLink.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        studentLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        addStudentLaebl.setFont(Font.font("Vedana", FontWeight.BOLD, 15));
        firstNameLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        lastNameLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        studentTeam.setFont(Font.font("Vedana", FontWeight.BOLD, 13));
        roleLabel.setFont(Font.font("Vedana", FontWeight.BOLD, 13));

    }
    
    public boolean getFirstImage1(){
        return firstImage1;
    }
    
    public boolean getFirstImage2(){
        return firstImage2;
    }
    
    public boolean getFirstImage3(){
        return firstImage3;
    }
    
    public void setFirstImage1(boolean val){
        firstImage1 = val;
    }
    
    public void setFirstImage2(boolean val){
        firstImage2 = val;
    }
    
    public void setFirstImage3(boolean val){
        firstImage3 = val;
    }
    
    public ImageView getIV1(){
        return iv1;
    }
    
    
    public void setIV1(javafx.scene.image.Image newImage){
        iv1.setImage(newImage);
        
    }
    
    public void setIV2(javafx.scene.image.Image newImage){
        iv2.setImage(newImage);
        
    }
    
    public void setIV3(javafx.scene.image.Image newImage){
        iv3.setImage(newImage);
        
    }
    
    public ChoiceBox getSubjectCombo(){
        return subjectCombo;
    }
    
    public ChoiceBox getCourseNumCombo(){
        return courseNumCombo;
        
    }
    
    public ChoiceBox getSemesterCombo(){
        return semesterCombo;

    }
    
    
    public Label getSiteTemplateLabel(){
        return siteTemplateLabel;
    }
    
    public ChoiceBox getYearCombo(){
        return yearCombo;
    }
    
    public ChoiceBox getSSCombo(){
        return ssCombo;
    }

    public ChoiceBox getRta1Combo(){
        return rta1Combo;
    }
    
    public ChoiceBox getRta2Combo(){
        return rta2Combo;
    }

    public ChoiceBox getScheduleTypeCombo(){
        return scheduleTypeCombo;
    }
    
    public ChoiceBox getTeamNameCombo(){
        return teamNameCombo;
    }
    
    public DatePicker getScheduleStart(){
        return scheduleStart;
    }
    
    public DatePicker getScheduleEnd(){
        return scheduleEnd;
    }
    
    public DatePicker getScheduleDate(){
        return scheduleDate;
    }
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }
    
    public ComboBox getOfficeHour(boolean start){
        if(start)
            return comboBox1;
        return comboBox2;
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    @Override
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
        
        titleText.clear();
        instructorNmaeText.clear();
        instructorHomeText.clear();
        rsectionText.clear();
        rinstructorText.clear();
        rDayText.clear();
        rlocText.clear();
        timeText.clear();
        TitleText.clear();
        topicText.clear();
        linkText.clear();
        criteriaText.clear();
        studentNameText.clear();
        studentLinkText.clear();
        firstNameText.clear();
        lastNameText.clear();
        roleText.clear();
        emailTextField.clear();
        nameTextField.clear();
        
        colorPicker1.setValue(null);
        colorPicker2.setValue(null);
        
        circle1.setFill(Color.WHITE);
        circle2.setFill(Color.WHITE);
        
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        TAData taData = ((AllData)dataComponent).getTAData();
        reloadOfficeHoursGrid(taData);
        addButton.setText(props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString()));
        add = true;
        
    }

    public void reloadOfficeHoursGrid(TAData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        TAStyle taStyle = (TAStyle)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        javafx.scene.image.Image buttonImage = new javafx.scene.image.Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        ImageView iv = new ImageView(buttonImage);
        iv.setFitWidth(18);
        iv.setFitHeight(18);
        button.setGraphic(iv);
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);
        
        toolbar.getChildren().add(button);
        
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }

    public TextField getTitleText() {
        return titleText;
    }

    public TextField getScheduleTitleText(){
        return TitleText;
    }
    
    public TextField getInstructorNmaeText() {
        return instructorNmaeText;
    }

    public TextField getInstructorHomeText() {
        return instructorHomeText;
    }

    public TextField getRsectionText() {
        return rsectionText;
    }

    public TextField getRinstructorText() {
        return rinstructorText;
    }

    public TextField getRlocText() {
        return rlocText;
    }

    public TextField getTimeText() {
        return timeText;
    }


    public TextField getTopicText() {
        return topicText;
    }

    public TextField getLinkText() {
        return linkText;
    }

    public TextField getCriteriaText() {
        return criteriaText;
    }

    public TextField getStudentNameText() {
        return studentNameText;
    }

    public TextField getStudentLinkText() {
        return studentLinkText;
    }

    public TextField getFirstNameText() {
        return firstNameText;
    }

    public TextField getLastNameText() {
        return lastNameText;
    }

    public TextField getRoleText() {
        return roleText;
    }
    
    public void setExportDir(String export){
        exportDir.set(export);
    }
    
    public void setTemplateDir(String template){
        templateDir = template;
    }
    
    public void setImagePath1(String image1){
        imagePath1 = image1;
    }
    
    public String getImagePath1(){
        return imagePath1;
    }
    
    
    
    public void setImagePath2(String image2){
        imagePath2 = image2;
    }
    
    public void setImagePath3(String image3){
        imagePath3 = image3;
    }
    
    public Label getExport(){
        return exportD;
    }
    
    public Label getTemplate(){
        return templateD;
    }
    
    
    
    public Label getCSSFile(){
        return cssFile;
    }
    
    
    public ImageView getIV2(){
        return iv2;
    }
    
    
    public ImageView getIV3(){
        return iv3;
    }
    
    public String getExportDir(){
        return exportDir.get();
    }
    
    public String getTemplateDir(){
        return templateDir;
    }
    
    public TextField getrDayText(){
        return rDayText;
    }
    
    public TableView getRecitationTable(){
        return recitationTable;
    }
    
    public Button getExpButton(){
        return expButton;
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    public TableView getScheduleTable(){
        return scheduleTable;
    }
    
    public ColorPicker getColorPicker1(){
        return colorPicker1;
    }
    
    public ColorPicker getColorPicker2(){
        return colorPicker2;
    }
    
    public Circle getCircle1(){
        return circle1;
    }
    
    public Circle getCircle2(){
        return circle2;
    }
    
    public TableView getTeamTable(){
        return teamTable;
    }
    
    public TableView getStudentTable(){
        return studentTable;
    }
}
