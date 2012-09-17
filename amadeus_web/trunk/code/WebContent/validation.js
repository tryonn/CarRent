var dtCh= "/";
var minYear = 1000;
var maxYear = 9999;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		alert("A data deve estar no formato : dd/mm/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Digite um mes valido")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Digite um dia valido")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Digite um ano de 4 caracteres entre "+minYear+" e "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Digite uma data valida")
		return false
	}
return true
}

function isHour(hrStr){
	var strHour = hrStr.substring(0,2);
	var strMinute = hrStr.substring(3,5);
	var strSecond = hrStr.substring(6,8);
	
	if(!isInteger(strHour)){
		alert("A hora informada e invalida.");
		return false;
	}
	if(!isInteger(strMinute)){
		alert("Os minutos informados sao invalidos.");
		return false;
	}
	if(!isInteger(strSecond)){
		alert("Os segundos informados sao invalidos.");
		return false;
	}
	
	var hour = parseInt(strHour);
	var minute = parseInt(strMinute);
	var second = parseInt(strSecond);
	
	if(hour <0 || hour >23){
		alert("A hora informada e invalida.");
		return false;
	}
	if(minute < 0 || minute > 59){
		alert("Os minutos informados sao invalidos.");
		return false;
	}
	if(second < 0 || second > 59){
		alert("Os segundos informados sao invalidos.");
		return false;
	}
	return true;
}
