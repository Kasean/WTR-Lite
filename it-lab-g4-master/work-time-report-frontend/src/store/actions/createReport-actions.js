import * as types from "../actions/action-types";

export function getReportProjects(projects) {
  return {
    type: types.GET_REPORT_PROJECTS,
    projects
  };
}

export function getReportFeatures(features) {
  return {
    type: types.GET_REPORT_FEATURES,
    features
  };
}

export function getReportTasks(tasks) {
  return {
    type: types.GET_REPORT_TASKS,
    tasks
  };
}

export function getReporTimeTask(timeTask) {
  return {
    type: types.GET_REPORT_TIME_TASK,
    timeTask
  };
}

export function getReportWorkUnits(workUnits) {
  return {
    type: types.GET_REPORT_WORK_UNITS,
    workUnits
  };
}

export function getReportFactors(factors) {
  return {
    type: types.GET_REPORT_FACTORS,
    factors
  };
}

export function getReportComment(comment) {
  return {
    type: types.GET_REPORT_COMMENT,
    comment
  };
}

export function updateReport(report) {
  return {
    type: types.UPDATE_REPORT,
    report
  };
}

export function getReportDate(date) {
  return {
    type: types.GET_REPORT_DATE,
    date
  };
}
