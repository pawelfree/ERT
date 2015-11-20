function mondaysOnlyWithDaysOff(date){	
	var day = date.getDay();
	if(day == 1){
		//sprawdzenie czy nie jest wylacozny przez dni wolne
		if(isDayOff(date)){
			return [false, ''];
		}
	}	
	return [(day == 1), '']
}

function sundaysOnlyWithDaysOff(date){	
	var day = date.getDay();
	if(day == 0){
		//sprawdzenie czy nie jest wylacozny przez dni wolne
		if(isDayOff(date)){
			return [false, ''];
		}
	}
	return [(day == 0), '']
}

function mondaysOnly(date){	
	var day = date.getDay();		
	return [(day == 1), '']
}


function sundaysOnly(date){	
	var day = date.getDay();
	return [(day == 0), '']
}

function isDayOff(date){	
	var daysOff = getDaysOff();
	for(day in daysOff){
		if(daysOff[day].getTime() == date.getTime()){
			return true;
		}
	}
	return false;
}
/**
 * Funcka blokujaca weekendy i dni wolne z kalendarza dni wolnych
 */
function blockDaysOffAndWeekends(date){	
	if(isWeekend(date) || isDayOff(date)){
		return[false,''];	
	}
	return [true, ''];
}

/**
 * Funkcja blokujca dni wolne zdefiniowane w kalendarzu dni wolnych
 * @param date
 * @returns {Array}
 */
function blockDaysOff(date){	
	if(isDayOff(date)){
		return[false,''];	
	}
	return [true, ''];
}

function isWeekend(date){
	var day = date.getDay();
	if(day == 0 || day ==6){
		return true;
	}
	else{
		return false;
	}
}