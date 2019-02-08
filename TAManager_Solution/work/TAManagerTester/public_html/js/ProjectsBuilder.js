// DATA TO LOAD
var work;
var daysOfWeek;
var redInc;
var greenInc;
var blueInc;

function Work(hSemester, hProjects) {
    this.semester = hSemester;
    this.projects = hProjects;
}
function Project(hName, hStudents, hLink) {
    this.name = hName;
    this.students = hStudents;
    this.link = hLink;
}
function initProjects() {
    var dataFile = "./js/ProjectsData.json";
    loadData(dataFile);
    
    var dataFile2 = "./js/CourseSite.json";
    loadData2(dataFile2);
    
}

function loadData2(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	initPage(json);
    });
}

function initPage(jsonFile){
    var subject = jsonFile.course_subject;
    var banner = jsonFile.course_subject + " " + jsonFile.course_number + " - " + jsonFile.course_semester
     + " " + jsonFile.course_year + "<br />" + jsonFile.course_title;
    var str = "./images";
    
    var school = str + jsonFile.school_image;
    var left = str + jsonFile.left_image;
    var right = str + jsonFile.right_image;
    var instructorHome = jsonFile.instructor_home;
    
    $("title").html(subject + " " + jsonFile.course_number);
    $("#banner").html(banner);
    $("#left_image").attr('src', left);
    $("#right_image").attr('src', right);
    $("#school_image").attr('src', school);
    $("#instructor_home").attr('href', instructorHome);
    
    
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        loadJSONData(json);
        addProjects();
    });
}
function loadJSONData(data) {
    // LOAD Projects DATA
    work = new Array();
    for (var i = 0; i < data.work.length; i++) {
        var workData = data.work[i];
        var wProjects = new Array();
        for (var j = 0; j < workData.projects.length; j++) {
            var projectData = workData.projects[j];
            var pStudents = new Array();
            for (var k = 0; k < projectData.students.length; k++) {
                pStudents[k] = projectData.students[k];
            }
            var project = new Project(projectData.name, pStudents, projectData.link);
            wProjects[j] = project;
        }
        wWork = new Work(data.work[i].semester, wProjects);
        work[i] = wWork;
    }
}

function addProjects() {
    var div = $("#project_tables");
    for (var i = 0; i < work.length; i++) {
        var wWork = work[i];
        var text = "<h3>" + wWork.semester + " Projects</h3>"
                + "<table><tbody>";        
        var projects = wWork.projects;
        for (var j = 0; j < projects.length; j+=2) {
            var project = projects[j];
            text += "<tr>";
            text += getProjectCell(project);
            if ((j + 1) < projects.length) {
                project = projects[j + 1];
                text += getProjectCell(project);
            }
            text += "</tr>";        
        }
        text += "</tbody></table><br /><br />";
        div.append(text);
    }
}
function getProjectCell(project) {
    var text = "<td><a href=\""
            + project.link
            + "\"><img src=\"./images/projects/"
            + project.name.replace(/\s/g, '')
            + ".png\" /></a><br />"
            + "<a href=\""
            + project.link
            + "\">" + project.name + "</a><br />"
            + "by ";
    for (var k = 0; k < project.students.length; k++) {
        text += project.students[k];
        if ((k + 1) < project.students.length)
            text += ", ";
    }
    text += "<br /><br /></td>";
    return text;
}