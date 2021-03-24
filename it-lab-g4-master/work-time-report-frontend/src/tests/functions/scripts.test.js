import { dayweek, getDateRangeOfWeek, weekNumber } from '../../functions/scripts.js';

it("get dates", () => {
    let expected = ["2012-12-03",
    "2012-12-04",
    "2012-12-05",
    "2012-12-06",
    "2012-12-07",
    "2012-12-08",
    "2012-12-09"]
    expect(dayweek("2012-12-03")).toStrictEqual(expected);
});

it("get date", () => {
    let expected = "2019-12-30"
    expect(getDateRangeOfWeek(1)).toStrictEqual(expected);
});

it("get num of week", () => {
    var currentDateTime = new Date();
    var startTimeOfCurrentYear = (new Date(currentDateTime.getFullYear(), 0, 1)).getTime();
    var pastTimeOfStartCurrentYear = currentDateTime - startTimeOfCurrentYear;
    let expected = Math.ceil((pastTimeOfStartCurrentYear / 3600000 / 168))
    expect(weekNumber()).toStrictEqual(expected);
});