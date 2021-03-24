import React from "react";
import s from "./SearchReport.module.css";
import CalendarButton from "../CalendarButton/CalendarButton";
import { connect } from "react-redux";
import * as selectActions from "../../store/actions/reports-action";
import moment from "moment";

import SearchReportService from "../../services/SearchReportService";
import SearchReportById from "../../services/SearchReportTableService";

class SearchReport extends React.Component {
  constructor(props) {
    super(props);
    this.sendData = this.sendData.bind(this);
    this.setDayFrom = this.setDayFrom.bind(this);
    this.setDayTo = this.setDayTo.bind(this);
    this.onInputDayStart = this.onInputDayStart.bind(this);
    this.onInputDayLast = this.onInputDayLast.bind(this);
    this.getAllProject = this.getAllProject.bind(this);
    this.getAllFeature = this.getAllFeature.bind(this);
    this.getAllFactor = this.getAllFactor.bind(this);
    this.onSelectProject = this.onSelectProject.bind(this);
    this.onSelectFeature = this.onSelectFeature.bind(this);
    this.onSelectFactor = this.onSelectFactor.bind(this);
    this.onSelectTask = this.onSelectTask.bind(this);
  }
  setDayFrom(value) {
    this.props.selectStartDay(value);
  }
  setDayTo(value) {
    this.props.selectLastDay(value);
  }
  onInputDayStart(value) {
    this.props.selectStartDay(value);
  }
  onInputDayLast(value) {
    this.props.selectLastDay(value);
  }

  getAllProject() {
    this.resetFeatures();
    this.resetTasks();
    SearchReportById.get("/project/")
      .then(response => {
        let project = [{ projectID: -1, projectName: "none" }];
        project = project.concat(response.data);
        this.props.getAllProject(project);
      })
      .catch(error => {
        alert("Error");
      });
  }
  getAllFactor() {
    SearchReportById.get("/factor")
      .then(response => {
        let factors = [{ id: -1, name: "none" }];
        factors = factors.concat(response.data);
        this.props.getAllFactor(factors);
      })
      .catch(error => {
        alert("Error");
      });
  }

  getAllFeature(projectID = -1) {
    this.resetTasks();
    SearchReportById.get("/feature/byProject/" + projectID)
      .then(response => {
        console.log("Feature");
        console.dir(projectID);
        let selectFeatures = [{ featureId: -1, name: "none" }];
        selectFeatures = selectFeatures.concat(response.data);
        this.props.getAllFeature(selectFeatures);
      })
      .catch(error => {
        console.error(error);
        alert("Error");
      });
  }

  getTaskByFeatureId(featureId = -1) {
    SearchReportById.get("/task/byFeature/" + featureId)
      .then(response => {
        let selectTask = [{ id: -1, name: "none" }];
        selectTask = selectTask.concat(response.data);
        this.props.getTaskByFeatureId(selectTask);
      })
      .catch(error => {
        console.error(error);
        alert("Error");
      });
  }

  onSelectProject(e) {
    const { projects } = this.props;
    let selectedProject = projects.find(obj => obj.projectID == e.target.value);
    this.props.getSelectedProjects(selectedProject);
    this.getAllFeature(selectedProject.projectID);
  }
  onSelectFeature(e) {
    const { feature } = this.props;
    console.log("feature", feature);
    let selectedFeature = feature.find(obj => obj.featureId == e.target.value);
    this.props.getSelectedFeature(selectedFeature);
    this.getTaskByFeatureId(selectedFeature.featureId);
  }
  onSelectTask(e) {
    const { task } = this.props;
    let selectedTasks = task.find(obj => obj.id == e.target.value);
    this.props.getSelectedTask(selectedTasks);
  }
  onSelectFactor(e) {
    const { factor } = this.props;
    let selectedFactors = factor.find(obj => obj.id == e.target.value);
    this.props.getSelectedFactor(selectedFactors);
  }
  resetFeatures() {
    let defaultFeatures = [{ featureId: -1, name: "none" }];
    this.props.getAllFeature(defaultFeatures);
  }
  resetTasks() {
    let defaultTasks = [{ id: -1, name: "none" }];
    this.props.getTaskByFeatureId(defaultTasks);
  }

  sendData() {
    const {
      lastDay,
      startDay,
      selectedProject,
      selectedFeature,
      selectedFactor,
      selectedTask
    } = this.props;
    let startDayParam = startDay ? `date=${startDay}` : "";
    let lastDayParam = lastDay ? `&dateF=${lastDay}` : "";
    let selectedFactorParam = "";
    if (
      selectedFactor !== undefined &&
      selectedFactor !== null &&
      selectedFactor.id !== -1
    ) {
      selectedFactorParam = `&factorId=${selectedFactor.id}`;
    }
    let selectedFeatureParam = "";
    if (
      selectedFeature !== undefined &&
      selectedFeature !== null &&
      selectedFeature.featureId !== -1
    ) {
      selectedFeatureParam = `&featureId=${selectedFeature.featureId}`;
    }
    let selectedProjectParam = "";
    if (
      selectedProject !== undefined &&
      selectedProject !== null &&
      selectedProject.projectID !== -1
    ) {
      selectedProjectParam = `&projectId=${selectedProject.projectID}`;
    }
    let selectedTaskParam = "";
    if (
      selectedTask !== undefined &&
      selectedTask !== null &&
      selectedTask.id !== -1
    ) {
      selectedTaskParam = `&taskId=${selectedTask.id}`;
    }
    let url = "";
    if (
      selectedTaskParam == "" &&
      selectedProjectParam == "" &&
      selectedFeatureParam == "" &&
      selectedFactorParam == ""
    ) {
      url = "/report/byUser/1?" + startDayParam + lastDayParam;
    } else {
      url =
        "/report/byUser/1/filter?" +
        startDayParam +
        lastDayParam +
        selectedFactorParam +
        selectedFeatureParam +
        selectedProjectParam +
        selectedTaskParam;
    }
    SearchReportService.get(
      url
    )
      .then(response => {
        console.log("response", response);
        this.props.getAllReports(response.data);
      })
      .catch(error => {
        this.props.getAllReports([]);
      });
  }
  componentDidMount() {
    this.getAllProject();
    this.getAllFeature();
    this.getAllFactor();
    this.initDays();
  }

  initDays = ( ) =>{
    let date = moment()
    .startOf("day")
    .format("YYYY-MM-DD")
    console.dir(date)
    this.setDayTo(date);
    this.setDayFrom(date)
    
  }

  render() {
    const { projects, feature, selectedProject, factor, task } = this.props;
    console.log("");
    console.dir(selectedProject);
    return (
      <div className={s.container}>
        <h1 className={s.h1}>SearchReport:</h1>
        <table className={s.table}>
          <tr>
            <td className={s.tdText}>Project/activity</td>
            <td className={s.tdButtonOne}>
              <select onChange={this.onSelectProject} className={s.select}>
                {projects.map(project => (
                  <option value={project.projectID} key={project.projectID}>
                    {project.projectName}
                  </option>
                ))}
              </select>
            </td>
            <td className={s.tdText}>Feature</td>
            <td className={s.tdButtonTwo}>
              <select onChange={this.onSelectFeature} className={s.select}>
                {feature.map(feature => (
                  <option value={feature.featureId} key={feature.featureId}>
                    {feature.name}
                  </option>
                ))}
              </select>
            </td>
          </tr>
          <tr>
            <td className={s.tdText}>Factor</td>
            <td className={s.tdButtonOne}>
              <select onChange={this.onSelectFactor} className={s.select}>
                {factor.map(factor => (
                  <option value={factor.id} key={factor.id}>
                    {factor.name}
                  </option>
                ))}
              </select>
            </td>
            <td className={s.tdText}>Task</td>
            <td className={s.tdButtonTwo}>
              <select onChange={this.onSelectTask} className={s.select}>
                {task.map(task => (
                  <option value={task.id} key={task.id}>
                    {task.name}
                  </option>
                ))}
              </select>
            </td>
          </tr>
          <tr>
            <td className={s.tdText}>Time period</td>
            <tr className={s.sectionCalendar}>
              <td className={s.tdButtonThree}>
                <CalendarButton
                  onSelectDay={this.setDayFrom}
                  onInputDay={this.onInputDayStart}
                />
              </td>
              <p>to</p>
              <td className={s.tdButtonThree}>
                <CalendarButton
                  onSelectDay={this.setDayTo}
                  onInputDay={this.onInputDayLast}
                />
              </td>
            </tr>
          </tr>
        </table>
        <input
          className={s.searchReportButton}
          type="button"
          value="Search report"
          onClick={this.sendData}
        />
      </div>
    );
  }
}
const mapStateToProps = state => {
  const { lastDay, startDay } = state.selectedDay;
  const {
    reports,
    projects,
    feature,
    selectedProject,
    factor,
    task,
    selectedFeature,
    selectedFactor,
    selectedTask
  } = state.valueReducer;
  return {
    lastDay,
    startDay,
    reports,
    projects,
    feature,
    selectedProject,
    selectedFeature,
    selectedFactor,
    selectedTask,
    factor,
    task
  };
};
export default connect(mapStateToProps, selectActions)(SearchReport);
