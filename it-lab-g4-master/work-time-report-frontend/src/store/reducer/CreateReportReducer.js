import * as types from "../actions/action-types";

const initialState = {
  projects: [],
  features: [],
  tasks: [],
  timeTask: 0,
  workUnits: 0,
  factors: [],
  comment: "",
  reportToCreate: { user: { userId: 1 }, status: "created" },
  date: new Date()
};

const getCreateReportReduser = (state = initialState, action) => {
  switch (action.type) {
    case types.GET_REPORT_PROJECTS:
      return { ...state, projects: action.projects };
    case types.GET_REPORT_FEATURES:
      return { ...state, features: action.features };
    case types.GET_REPORT_TASKS:
      return { ...state, tasks: action.tasks };
    case types.GET_REPORT_TIME_TASK:
      return { ...state, timeTask: action.timeTask };
    case types.GET_REPORT_WORK_UNITS:
      return { ...state, workUnits: action.workUnits };
    case types.GET_REPORT_FACTORS:
      return { ...state, factors: action.factors };
    case types.GET_REPORT_COMMENT:
      return { ...state, comment: action.comment };
    case types.UPDATE_REPORT:
      console.dir(action);
      let obj = {
        ...state,
        reportToCreate: { ...state.reportToCreate, ...action.report }
      };
      console.dir(obj);
      return obj;
    case types.GET_REPORT_DATE:
      return { ...state, date: action.date };

    default:
      return state;
  }
};

export default getCreateReportReduser;
