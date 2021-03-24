import * as types from "../actions/action-types";

const initialState = {
  reports: [],
  projects: [],
  selectedProject: null,
  selectedFeature: null,
  selectedFactor: null,
  selectedTask: null,
  feature: [],
  factor: [],
  task: []
};

const valueReducer = (state = initialState, action) => {
  switch (action.type) {
    case types.GET_ALL_REPORTS:
      return Object.assign({}, state, { reports: action.reports });
    case types.GET_ALL_PROJECT:
      return Object.assign({}, state, { projects: action.projects });
    case types.GET_SELECTED_PROJECT:
      return Object.assign({}, state, {
        selectedProject: action.selectedProject
      });
    case types.GET_SELECTED_FEATURE:
      return Object.assign({}, state, {
        selectedFeature: action.selectedFeature
      });
    case types.GET_SELECTED_FACTOR:
      return Object.assign({}, state, {
        selectedFactor: action.selectedFactor
      });
    case types.GET_SELECTED_TASK:
      return Object.assign({}, state, {
        selectedTask: action.selectedTask
      });
    case types.GET_ALL_FEATURE:
      return Object.assign({}, state, { feature: action.feature });
    case types.GET_ALL_FACTOR:
      return Object.assign({}, state, { factor: action.factor });
    case types.GET_ALL_TASK:
      return Object.assign({}, state, { task: action.task });
    default:
      return state;
  }
};

export default valueReducer;
