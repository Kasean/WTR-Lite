import * as types from "../actions/action-types";

export function getAllReports(reports) {
  return {
    type: types.GET_ALL_REPORTS,
    reports
  };
}
export function getAllProject(projects) {
  return {
    type: types.GET_ALL_PROJECT,
    projects
  };
}
export function getSelectedProjects(selectedProject) {
  return {
    type: types.GET_SELECTED_PROJECT,
    selectedProject
  };
}
export function getSelectedFeature(selectedFeature) {
  return {
    type: types.GET_SELECTED_FEATURE,
    selectedFeature
  };
}
export function getSelectedFactor(selectedFactor) {
  return {
    type: types.GET_SELECTED_FACTOR,
    selectedFactor
  };
}
export function getSelectedTask(selectedTask) {
  return {
    type: types.GET_SELECTED_TASK,
    selectedTask
  };
}
export function getAllFactor(factor) {
  return {
    type: types.GET_ALL_FACTOR,
    factor
  };
}

export function getAllFeature(feature) {
  return {
    type: types.GET_ALL_FEATURE,
    feature
  };
}
export function getTaskByFeatureId(task) {
  return {
    type: types.GET_ALL_TASK,
    task
  };
}
export function selectStartDay(startDay) {
  return {
    type: types.SELECT_START_DAY,
    startDay
  };
}
export function selectLastDay(lastDay) {
  return {
    type: types.SELECT_LAST_DAY,
    lastDay
  };
}



