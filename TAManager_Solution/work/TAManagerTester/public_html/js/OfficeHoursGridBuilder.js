// DATA TO LOAD
var startHour;
var endHour;
var daysOfWeek;
var officeHours;
var undergradTAs;

function buildOfficeHoursGrid() {
    var dataFile = "./js/OfficeHoursGridData.json";
    loadData(dataFile, loadOfficeHours);
    
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



function loadData(jsonFile, callback) {
    $.getJSON(jsonFile, function(json) {
        callback(json);
    });
}

function loadOfficeHours(json) {
    initDays(json);
    addUndergradTAs(json);
    addGraduateTAs(json);
    addOfficeHours(json);
}

function initDays(data) {
    // GET THE START AND END HOURS
    startHour = parseInt(data.startHour);
    endHour = parseInt(data.endHour);

    // THEN MAKE THE TIMES
    daysOfWeek = new Array();
    daysOfWeek[0] = "MONDAY";
    daysOfWeek[1] = "TUESDAY";
    daysOfWeek[2] = "WEDNESDAY";
    daysOfWeek[3] = "THURSDAY";
    daysOfWeek[4] = "FRIDAY";    
}

function addUndergradTAs(data) {
    var tas = $("#undergrad_tas");
    var tasPerRow = 4;
    var numTAs = data.undergrad_tas.length;
    for (var i = 0; i < data.undergrad_tas.length; ) {
        var text = "";
        text = "<tr>";
        for (var j = 0; j < tasPerRow; j++) {
            text += buildTACell(i, numTAs, data.undergrad_tas[i]);
            i++;
        }
        text += "</tr>";
        tas.append(text);
    }
}

function addGraduateTAs(data) {
    var tas = $("#graduate_tas");
    var tasPerRow = 4;
    var numTAs = data.graduate_tas.length;
    for (var i = 0; i < data.graduate_tas.length; ) {
        var text = "";
        text = "<tr>";
        for (var j = 0; j < tasPerRow; j++) {
            text += buildTACell(i, numTAs, data.graduate_tas[i]);
            i++;
        }
        text += "</tr>";
        tas.append(text);
    }
}

function buildTACell(counter, numTAs, ta) {
    if (counter >= numTAs)
        return "<td></td>";

    var name = ta.name;
    var abbrName = name.replace(/\s/g, '');
    var email = ta.email;
    var text = "<td class='tas'><img width='100' height='100'"
                + " src='./images/tas/" + abbrName + ".JPG' "
                + " alt='" + name + "' /><br />"
                + "<strong>" + name + "</strong><br />"
                + "<span class='email'>" + email + "</span><br />"
                + "<br /><br /></td>";
    return text;
}
function addOfficeHours(data) {
    for (var i = startHour; i < endHour; i++) {
        // ON THE HOUR
        var textToAppend = "<tr>";
        var amPm = getAMorPM(i);
        var displayNum = i;
        if (i > 12)
            displayNum = displayNum-12;
        textToAppend += "<td>" + displayNum + ":00" + amPm + "</td>"
                    + "<td>" + displayNum + ":30" + amPm + "</td>";
        for (var j = 0; j < 5; j++) {
            textToAppend += "<td id=\"" + daysOfWeek[j]
                                + "_" + displayNum
                                + "_00" + amPm
                                + "\" class=\"open\"></td>";
        }
        textToAppend += "</tr>"; 
        
        // ON THE HALF HOUR
        var altAmPm = amPm;
        if (displayNum === 11)
            altAmPm = "pm";
        var altDisplayNum = displayNum + 1;
        if (altDisplayNum > 12)
            altDisplayNum = 1;
                    
        textToAppend += "<tr>";
        textToAppend += "<td>" + displayNum + ":30" + amPm + "</td>"
                    + "<td>" + altDisplayNum + ":00" + altAmPm + "</td>";
            
        for (var j = 0; j < 5; j++) {
            textToAppend += "<td id=\"" + daysOfWeek[j]
                                + "_" + displayNum
                                + "_30" + amPm
                                + "\" class=\"open\"></td>";
        }
        
        textToAppend += "</tr>";
        var cell = $("#office_hours_table");
        cell.append(textToAppend);
    }
    
    // NOW SET THE OFFICE HOURS
    for (var i = 0; i < data.officeHours.length; i++) {
	var id = data.officeHours[i].day + "_" + data.officeHours[i].time;
	var name = data.officeHours[i].name;
	var cell = $("#" + id);
	if (name === "Lecture") {
	    cell.removeClass("open");
	    cell.addClass("lecture");
	    cell.html("Lecture");
	}
	else {
	    cell.removeClass("open");
	    cell.addClass("time");
            if (cell.html().toString().length == 0)
                cell.append(name);
            else
        	cell.append("<br />" + name);
	}
    }
}
function getAMorPM(testTime) {
    if (testTime >= 12)
        return "pm";
    else
        return "am";
}