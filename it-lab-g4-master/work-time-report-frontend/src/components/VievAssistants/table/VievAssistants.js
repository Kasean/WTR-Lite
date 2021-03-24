import React from "react";
import s from "./VievAssistants.module.css";
import CreateReportService from "../../../services/CreateReportService";
import { connect } from "react-redux";
import * as selectActions from "../../../store/actions/createReport-actions";
import { Redirect, Route } from "react-router-dom";
import { useHistory } from "react-router-dom";


class CreateReportTable extends React.Component {
  constructor(props) {
    super(props);
    console.dir(this.props.projects);
    this.state = {
      timeTask: 0,
      workUnits: 0,
      projects: [],
      features: [{ featureID: -1, name: "none" }],
      tasks: [{ featureID: -1, name: "none" }],
      factors: [],
      nameFeatures: [],
      workUnits: 0,
      comments: "",
      reportToCreate: {},
      redirect: null,
      validReport: false
    };
    this.date = new Date();
    this.dateCount = this.date.toISOString();

    this.handleProjectChange = this.handleProjectChange.bind(this);
    this.handleFeatureChange = this.handleFeatureChange.bind(this);
    this.inputHour = this.inputHour.bind(this);
    this.handleChangeComment = this.handleChangeComment.bind(this);
    this.postReport = this.postReport.bind(this);
  }

  initProjects() {
    console.dir("qegqeg");
    CreateReportService.get("/project/")
      .then(response => {
        console.dir("weg");
        console.dir(response);
        console.dir(this.props);
        this.props.getReportProjects(response.data);
      })
      .catch(error => {
        console.error(error);
        alert("Ошибка");
      });
  }

  getFeaturesByProjectId(projectId) {
    CreateReportService.get("/feature/" + projectId)
      .then(response => {
        console.log("feature--------------------------", response.data);
        const resultFeatureDate = response.data.length
          ? response.data
          : [response.data];
        this.props.getReportFeatures(resultFeatureDate);
      })
      .catch(error => {
        console.error(error);
        alert("Ошибка");
      });
    // this.props.features = new Array([{ featureID: -1, name: "none" }]);
    //this.props.tasks = new Array([{ featureID: -1, name: "none" }]);
  }

  getTaskByFeatureId(featureId) {
    CreateReportService.get("/task/byFeature/" + featureId)
      .then(response => {
        console.dir(response);
        this.props.getReportTasks(response.data);
      })
      .catch(error => {
        console.error(error);
        alert("Please, select features!");
      });
  }

  initFactor() {
    console.dir("qegqeg");
    CreateReportService.get("/factor/")
      .then(response => {
        console.dir("weg");
        console.dir(response);
        console.dir(this.props);
        this.props.getReportFactors(response.data);
      })
      .catch(error => {
        console.error(error);
        alert("Ошибка");
      });
  }


  componentDidMount() {
    console.dir("wegweg");
    console.dir(this.props);

    this.initProjects();
    this.initFactor();
  }

  handleProjectChange(selectedValue) {
    let selectedProjectID = selectedValue.target.value;
    console.log("it is " + selectedProjectID);
    let selectedProject = this.props.projects.find(
      obj => obj.projectID == selectedProjectID
    );
    let reportToCreate = this.props.reportToCrate;
    let cloned = { ...reportToCreate, project: selectedProject };
    console.dir(cloned);
    this.props.updateReport(cloned);
    console.log(selectedProject);
    //this.props.getReportProjects(selectedProject);
    this.getFeaturesByProjectId(selectedProjectID);
    console.dir(this.props);
  }

  handleFeatureChange(selectedValue) {
    let selectedFeatureID = selectedValue.target.value;
    console.log("it is " + selectedFeatureID);
    let selectedFeature = this.props.features.find(
      obj => obj.featureId == selectedFeatureID
    );
    let featureToCreate = this.props.reportToCreate;
    console.dir(featureToCreate);
    let cloned = { ...featureToCreate, feature: selectedFeature };
    console.dir(cloned);
    this.props.updateReport(cloned);
    console.dir(selectedFeature);
    this.getTaskByFeatureId(selectedFeatureID);
    console.dir(this.props);
  }

  handleTaskChange = selectedValue => {
    let selectedTaskID = selectedValue.target.value;
    console.log("it is " + selectedTaskID);
    let selectedTask = this.props.tasks.find(obj => obj.id == selectedTaskID);
    let taskToCreate = this.props.reportToCreate;
    console.dir(taskToCreate);
    let cloned = { ...taskToCreate, task: selectedTask };
    console.dir(cloned);
    this.props.updateReport(cloned);
  };

  inputHour(timeTask) {
    let selectedHour = +timeTask.target.value;
    let hourToCreate = this.props.reportToCreate;

    if (Number.isNaN(selectedHour) || selectedHour < 0 || selectedHour > 99) {
      return;
    }
    console.log(+timeTask.target.value);
    let cloned = { ...hourToCreate, hours: selectedHour };
    this.setState({ timeTask: selectedHour });
    this.props.updateReport(cloned);
  }

  inputWorkUnits = workUnits => {
    let selectedHour = +workUnits.target.value;
    let hourToCreate = this.props.reportToCreate;

    if (Number.isNaN(selectedHour) || selectedHour < 0 || selectedHour > 99) {
      return;
    }
    console.log(+workUnits.target.value);
    let cloned = { ...hourToCreate, workUnits: selectedHour };
    this.setState({ workUnits: selectedHour });
    this.props.updateReport(cloned);
  };

  handleFactorChange = selectedValue => {
    let selectedFactorID = selectedValue.target.value;
    console.log("it is " + selectedFactorID);
    let selectedFactor = this.props.factors.find(
      obj => obj.id == selectedFactorID
    );
    let factorToCreate = this.props.reportToCreate;
    console.dir(factorToCreate);
    let cloned = { ...factorToCreate, factor: selectedFactor };
    console.dir(cloned);
    this.props.updateReport(cloned);
  };

  handleChangeComment(event) {
    let selectedComment = event.target.value;
    this.setState({
      comments: selectedComment
    });
    let commentToCreate = this.props.reportToCreate;
    let cloned = { ...commentToCreate, comment: selectedComment };
    this.props.updateReport(cloned);
  }

   redirectToHome = () => {
    const history = useHistory();
  
      history.push("/home");
    }

  postReport = (event) => {

    const reportDay = this.props.date;
    let reportToCreate = this.props.reportToCreate;
    console.log(reportToCreate);
    let cloned = { ...reportToCreate, date: reportDay };
    this.props.updateReport(cloned);
    console.log(cloned);
    console.dir(this.state);

    CreateReportService.post("/report/", reportToCreate, {
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(response => {
        console.dir(response);
        this.props.updateReport(response.data);
        let valid = window.confirm("Вы действительно хотите отправить отчет?");
        this.setState({validReport: valid});
      })
      .catch(error => {
        console.error(error);
        alert("Заполните все поля!");
      });
  }
  render() {
    let {
      projects,
      features,
      tasks,
      factors,
      timeTask,
      workUnits,
      comments
    } = this.props;


    if(this.state.validReport == true){
      alert("Отчет успешно сохранен!");
      return <Redirect to = "/myReports"/>
    }
    return (
      <div className="reportCreateTable">
        <table className="reportTable">
          <tr>
            <td className={s.td}>
              <select
                className={s.scrollMenu}
                onChange={this.handleProjectChange}
              >
                <option>none</option>
                {projects.map(proj => (
                  <option value={proj.projectID}>{proj.projectName}</option>
                ))}
              </select>
            </td>
            <td className={s.td}>
              <select
                className={s.scrollMenu}
                onChange={this.handleFeatureChange}
              >
                <option>none</option>
                {features.map(feature => (
                  <option value={feature.featureId} key={feature.featureId}>
                    {feature.name}
                  </option>
                ))}
              </select>
            </td>
            <td className={s.td}>
              <select className={s.scrollMenu} onChange={this.handleTaskChange}>
                <option>none</option>
                {tasks.map(task => (
                  <option value={task.id} key={task.id}>
                    {task.name}
                  </option>
                ))}
              </select>
            </td>
            <td className={s.td}>
              <input
                className={s.taskTime}
                value={this.state.timeTask}
                onChange={this.inputHour}
              ></input>
            </td>
            <td className={s.td}>
              <input
                className={s.taskTime}
                value={this.state.workUnits}
                onChange={this.inputWorkUnits}
              ></input>
            </td>
            <td className={s.td}>
              <select
                className={s.scrollMenu}
                onChange={this.handleFactorChange}
              >
                <option>none</option>
                {factors.map(fact => (
                  <option value={fact.id} key={fact.id}>
                    {fact.name}
                  </option>
                ))}
              </select>
            </td>
            <td className={s.td}>
              <input
                className={s.comment}
                value={this.state.comments}
                onChange={this.handleChangeComment}
              ></input>
            </td>
          </tr>
        </table>
        
        <button
          class={s.registerReport}
          type="submit"
          onClick={this.postReport}
        >
          Register report
        </button>
      </div>
    );
  }
}
const mapStateToProps = state => {
  const {
    projects,
    features,
    tasks,
    timeTask,
    workUnits,
    factors,
    comment,
    reportToCreate
  } = state.CreateReportReducer;
  return {
    projects,
    features,
    tasks,
    timeTask,
    workUnits,
    factors,
    comment,
    reportToCreate
  };
};
export default connect(mapStateToProps, selectActions)(CreateReportTable);
