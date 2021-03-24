Date.prototype.getWeekNumber = function () {
	    var target  = new Date(this.valueOf());
	    var dayNr   = (this.getDay() + 6) % 7;
	    target.setDate(target.getDate() - dayNr + 3);
	    var firstThursday = target.valueOf();
	    target.setMonth(0, 1);
	    if (target.getDay() != 4) {
	        target.setMonth(0, 1 + ((4 - target.getDay()) + 7) % 7);
	    }
	    return 1 + Math.ceil((firstThursday - target) / 604800000);
}
	
export function weekNumber(){ 

    var date = new Date(); 
    return date.getWeekNumber(); 

}


export function getDateRangeOfWeek(weekNo){
	var d1 = new Date();

	d1.setDate(d1.getDate() - eval(d1.getDay()- 1));
	var weekNoToday = d1.getWeekNumber();
	var weeksInTheFuture = eval( weekNo - weekNoToday );
	d1.setDate(d1.getDate() + eval( 7 * weeksInTheFuture ));
	var aMonth = eval(d1.getMonth()+1);
	if (aMonth < 10)
	{
		aMonth = "0"+aMonth;
	}
	var aDay = d1.getDate();
	if (aDay < 10)
	{
		aDay = "0"+aDay;
	}
	var rangeIsFrom =  d1.getFullYear() + "-" + aMonth + "-" + aDay;
	return rangeIsFrom;
}
	
export function dayweek(date){
let curr = new Date(date); 
let week = [];
for (let i = 1; i <= 7; i++) {
  let first = curr.getDate() - curr.getDay() + i; 
  let day = new Date(curr.setDate(first)).toISOString().slice(0, 10);
  week.push(day);
}
return week;
}






export function dayComment(event) {
    let selectedComment = event.target.value;
    this.setState({
      comments: selectedComment
    });
    let commentToCreate = this.props.reportToCreate;
    let cloned = { ...commentToCreate, comment: selectedComment };
    this.props.updateReport(cloned);
  }


  export function workHour(timeTask) {
    let selectedHour = +timeTask.target.value;
    let hourToCreate = this.props.reportToCreate;

    if (Number.isNaN(selectedHour) || selectedHour < 0 || selectedHour > 99) {
      return;
    }
    console.log(+timeTask.target.value);
    let cloned = { ...hourToCreate, timeTask: selectedHour };
    this.setState({ timeTask: selectedHour });
    this.props.updateReport(cloned);
  }