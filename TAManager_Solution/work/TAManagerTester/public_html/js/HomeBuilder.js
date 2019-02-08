/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initHome(){
    var dataFile = "./js/CourseSite.json";
    loadData(dataFile);
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	initPage(json);
    });
}

function initPage(jsonFile){
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
    
    $("#left_image").attr('src', left);
    $("#right_image").attr('src', right);
    $("#school_image").attr('src', school);
    
}