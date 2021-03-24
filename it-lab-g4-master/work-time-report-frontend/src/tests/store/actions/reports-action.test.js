import { getAllReports, getAllProject, getSelectedProjects, getAllFactor, getAllFeature, selectStartDay, selectLastDay } from '../../../store/actions/reports-action'

it('getAllReports', () => {
    let expected = {
        "reports": undefined,
        "type": "GET_ALL_REPORTS"
    }
    expect(getAllReports()).toStrictEqual(expected);
  });

  it('getAllProject', () => {
    let expected = {
        "projects": undefined,
        "type": "GET_ALL_PROJECT"
    }
    expect(getAllProject()).toStrictEqual(expected);
  });

  it('getSelectedProjects', () => {
    let expected = {
        "selectedProject": undefined,
        "type": "GET_SELECTED_PROJECT"
    }
    expect(getSelectedProjects()).toStrictEqual(expected);
  });

  it('getAllFactor', () => {
    let expected = {
        "factor": undefined,
        "type": "GET_ALL_FACTOR"
    }
    expect(getAllFactor()).toStrictEqual(expected);
  });

  it('getAllFeature', () => {
    let expected = {
        "feature": undefined,
        "type": "GET_ALL_FEATURE"
    }
    expect(getAllFeature()).toStrictEqual(expected);
  });

  it('selectStartDay', () => {
    let expected = {
        "startDay": undefined,
        "type": "SELECT_START_DAY"
    }
    expect(selectStartDay()).toStrictEqual(expected);
  });

  it('selectLastDay', () => {
    let expected = {
        "lastDay": undefined,
        "type": "SELECT_LAST_DAY"
    }
    expect(selectLastDay()).toStrictEqual(expected);
  });