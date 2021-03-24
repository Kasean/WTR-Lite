import { getReportProjects, getReportFeatures, getReportTasks, getReporTimeTask, getReportWorkUnits, getReportFactors, getReportComment, updateReport } from '../../../store/actions/createReport-actions'

it('getReportProjects', () => {
    let expected = {
        "projects": undefined,
        "type": "GET_REPORT_PROJECTS"
    }
    expect(getReportProjects()).toStrictEqual(expected);
  });

  it('getReportFeatures', () => {
    let expected = {
        "features": undefined,
        "type": "GET_REPORT_FEATURES"
    }
    expect(getReportFeatures()).toStrictEqual(expected);
  });

  it('getReportTasks', () => {
    let expected = {
        "tasks": undefined,
        "type": "GET_REPORT_TASKS"
    }
    expect(getReportTasks()).toStrictEqual(expected);
  });

  it('getReporTimeTask', () => {
    let expected = {
        "timeTask": undefined,
        "type": "GET_REPORT_TIME_TASK"
    }
    expect(getReporTimeTask()).toStrictEqual(expected);
  });

  it('getReportWorkUnits', () => {
    let expected = {
        "workUnits": undefined,
        "type": "GET_REPORT_WORK_UNITS"
    }
    expect(getReportWorkUnits()).toStrictEqual(expected);
  });

  it('getReportFactors', () => {
    let expected = {
        "factors": undefined,
        "type": "GET_REPORT_FACTORS"
    }
    expect(getReportFactors()).toStrictEqual(expected);
  });

  it('getReportComment', () => {
    let expected = {
        "comment": undefined,
        "type": "GET_REPORT_COMMENT"
    }
    expect(getReportComment()).toStrictEqual(expected);
  });

  it('updateReport', () => {
    let expected = {
        "report": undefined,
        "type": "UPDATE_REPORT"
    }
    expect(updateReport()).toStrictEqual(expected);
  });