import { combineReducers } from "redux";
import valueReducer from "./MyReportsReducer";
import selectDayReducer from "./MyReportsSearchDateSelectorReducer";
import getCreateReportReduser from "./CreateReportReducer";

let reducers = combineReducers({
  valueReducer,
  selectedDay: selectDayReducer,
  CreateReportReducer: getCreateReportReduser
});

export default reducers;
