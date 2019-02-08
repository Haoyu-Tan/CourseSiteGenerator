/* global jsonFile */

// CONSTANTS
var IMG_PATH;
var LEAD_ROLE;
var PM_ROLE;
var DESIGN_ROLE;
var DATA_ROLE;
var MOBILE_ROLE;
var NONE;
var AVAILABLE_RED;
var AVAILABLE_GREEN;
var AVAILABLE_BLUE;

// DATA TO LOAD
var teams;
var teamNames;
var availableStudents;

function Team(initName, initRed, initGreen, initBlue, textRed, textGreen, textBlue) {
    this.name = initName;
    this.red = initRed;
    this.green = initGreen;
    this.blue = initBlue;
    this.students = new Array();
    this.text_red = textRed;
    this.text_green = textGreen;
    this.text_blue = textBlue;
}

function Student(initLastName, initFirstName, initTeam, initRole) {
    this.lastName = initLastName;
    this.firstName = initFirstName;
    this.team = initTeam;
    this.role = initRole;
}

function initTeamsAndStudents() {
    IMG_PATH = "./images/students/";
    LEAD_ROLE = "Lead Programmer";
    PM_ROLE = "Project Manager";
    DESIGN_ROLE = "Lead Designer";
    DATA_ROLE = "Data Designer";
    MOBILE_ROLE = "Mobile Developer";
    NONE = "none";
    AVAILABLE_RED = 120;
    AVAILABLE_GREEN = 200;
    AVAILABLE_BLUE = 70;
    teams = new Array();
    teamNames = new Array();
    availableStudents = new Array();
    var dataFile = "./js/TeamsAndStudents.json";
    loadData(dataFile);
    
    var dataFile = "./js/CourseSite.json";
    loadData2(dataFile);
    
}

function loadData2(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	initPage2(json);
    });
}

function initPage2(jsonFile){
    var subject = jsonFile.course_subject;
    var banner = jsonFile.course_subject + " " + jsonFile.course_number + " - " + jsonFile.course_semester
     + " " + jsonFile.course_year + "<br />" + jsonFile.course_title;
    
    $("title").html(subject + " " + jsonFile.course_number);
    $("#banner").html(banner);
    
    var str = "./images";
    
    var school = str + jsonFile.school_image;
    var left = str + jsonFile.left_image;
    var right = str + jsonFile.right_image;
    var instructorHome = jsonFile.instructor_home;
    var css = "./css" + jsonFile.css;
    var instructorName = jsonFile.instructor_name;
    
    
    $("#left_image").attr('src', left);
    $("#right_image").attr('src', right);
    $("#school_image").attr('src', school);
    $("#css_file").attr('href', css);
    $("#instructor_info").attr('href', instructorHome);
    $("#instructor_info").html(instructorName);
    
}



function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	loadTeams(json);
	loadStudents(json);
	initPage();
    });
}

function loadTeams(data) {
    for (var i = 0; i < data.teams.length; i++) {
	var rawTeam = data.teams[i];
	var team = new Team(rawTeam.name, rawTeam.red, rawTeam.green, rawTeam.blue, rawTeam.text_red, rawTeam.text_green, rawTeam.text_blue);
	teams[team.name] = team;
	teamNames[i] = team.name;
    }
}

function loadStudents(data) {
    var counter = 0;
    for (var i = 0; i < data.students.length; i++) {
	var rawStudent = data.students[i];
	var student = new Student(rawStudent.lastName, rawStudent.firstName, rawStudent.team, rawStudent.role);
	if (student.team == NONE)
	    availableStudents[counter++] = student;
	else {
	    var team = teams[student.team];
	    team.students[student.role] = student;
	}
    }
}

function initPage() {
    // FIRST ADD THE TEAM TABLES
    for (var i = 0; i < teamNames.length; i++) {
	var team = teams[teamNames[i]];
        var teamRGB = 'rgb(' + team.red + ',' + team.green + ',' + team.blue + ')';
        var textRGB = 'rgb(' + team.text_red + ',' + team.text_green + ',' + team.text_blue + ')';
	var teamText =
		"<table style='background-color:" + teamRGB + "'>\n"
		+ "<tr>\n"
		+ team.name + "<br /></td>\n"
		+ "</tr>\n"
		+ "<tr>\n";
	teamText = teamText + addStudentToTeam(team.students[LEAD_ROLE], textRGB) + "<tr>\n";
	teamText = teamText + addStudentToTeam(team.students[PM_ROLE], textRGB) + "<tr>\n";
	teamText = teamText + addStudentToTeam(team.students[DESIGN_ROLE], textRGB) +"<tr>\n";
	teamText = teamText +  addStudentToTeam(team.students[DATA_ROLE], textRGB) + "<tr>\n";
	teamText += "</tr>\n"
		+ "</table><br />\n";
	$("#teams_tables").append(teamText);
    }

    // THEN ADD THE REMAINING AVAILABLE STUDENTS
/*    var teamText =
	    "<table style='background:rgb("
	    + AVAILABLE_RED + ","
	    + AVAILABLE_GREEN + ","
	    + AVAILABLE_BLUE + ")'>\n"
		+ "<tr>\n"
		+ "<td colspan='4' style='font-size:16pt'><strong>No Team Yet</strong><br /><br /></td>\n"
		+ "</tr>\n"
		+ "<tr>\n";
    for (var i = 0; i < availableStudents.length; ) {
	teamText += "<tr>\n";
	for (var j = 0; j < 4; j++) {
	    if (i < availableStudents.length) {
		teamText += addStudentToTeam(availableStudents[i]);
		i++;
	    }
	}
	teamText += "</tr>\n";
    }
    teamText += "</table><br />\n";
    $("#teams_tables").append(teamText);
    */
}

function addStudentToTeam(student, text_color) {
    var text = "<td style='color:" + text_color + "'>\n"
	    + "<br clear='all' /><strong>" + student.firstName + " " + student.lastName;
    if (student.role != NONE) {
	text +=	" : " + student.role; 
    }
    text += "<br /><br /></strong></td>\n";
    return text;
}


function cleanText(text) {
    return text.replace("-", "").replace(" ", "");
}