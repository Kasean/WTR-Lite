import React from "react";
import s from "./EditReportweek.module.css";
import Header from "./../Header/Header";
import Menu from "./../MenuPage/MenuPage";
import Footer from "./../Footer/Footer";
import FactorService from "../../services/FactorService";
import CreateReportService from "../../services/CreateReportService";

import { getDateRangeOfWeek } from '../../functions/scripts.js';
import { dayweek } from '../../functions/scripts.js';

class EditReportforweek extends React.Component {
    constructor(props) {
        super(props);
        console.dir(this.props.projects);
    this.state = {
      timeTask: 0,
      workUnits: 0,
      projects: [],
      features: [],
      tasks: [],
      factors: [],
      nameFeatures: [],
      comments: "",
      reportToCreate: {}
    };

        const { fromNotifications, userId } = this.props.location.state;
        let arrweek= ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
        let arr=[];
        let arrdayweek = dayweek(getDateRangeOfWeek(fromNotifications));
        for (let i=0; i<arrweek.length; i++){
            arr[i] = {
               hours: 0,
               workUnits: 0,
               factorId: "",
               comment: ""
               };
        }
            this.state =  {
            day: arr,
            factor: [],
            projects: [],
            projectId: "",
            featureId: "",
            taskId: "",
            features: [],
            tasks: [],
            timeTask: 0,
            blocks: arrweek,
            blocksDate: arrdayweek,
            userId: userId

        };
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeComment = this.handleChangeComment.bind(this);
        this.handleProjectChange = this.handleProjectChange.bind(this);
        this.handleFeatureChange = this.handleFeatureChange.bind(this);
        this.handleTaskChange = this.handleTaskChange.bind(this);
        this.initProjects = this.initProjects.bind(this);
        this.inputHour = this.inputHour.bind(this);
    }

    initProjects(userId) {
      console.dir("qegqeg");
      FactorService.get("/project/byuser/" + userId)
        .then(response => {
          console.dir("weg");
          console.dir(response);
          console.dir(this.props);
          this.setState({projects:response.data})
        })
        .catch(error => {
          console.error(error);
          alert("Error projects");
        });
    }
    
    getFeaturesByProjectId(projectId) {
      FactorService.get("/feature/byProject/" + projectId)
        .then(response => {
          console.log("feature--------------------------", response.data);
          this.setState({features:response.data})
        })
        .catch(error => {
          console.error(error);
          alert("Ошибка");
        });
    }
    
    getTaskByFeatureId(featureId) {
      FactorService.get("/task/byFeature/" + featureId)
        .then(response => {
          console.dir(response);
          this.setState({tasks:response.data})
        })
        .catch(error => {
          console.error(error);
          alert("Please, select features!");
        });
    }

    componentDidMount() {
      this.initProjects(this.state.userId);
        console.dir("wegweg");
        console.dir(this.props);
     
    const { fromNotifications } = this.props.location.state;

    console.log("factor " + getDateRangeOfWeek(fromNotifications));
        FactorService
        .get("/factor")
         .then(response => {
         console.dir(response);
         this.setState({factor:response.data})
         })
         .catch(error => {
         console.error(error);
         alert("Ошибка получения фактора по Id.");
         });
    }

  handleChange(event, Id) {
      let perem = event.target.value;
      let lState = this.state.day.slice();
      lState[Id].factorId = perem;
      console.log("LOOOOOOOOOOOOOOOOOOOOOk", perem, lState);
      this.setState({ day: lState });
    }
    
    handleChangeComment(event, Id) {
      /*let selectedComment = event.target.value;
      this.setState({
        comments: selectedComment
      });
      let commentToCreate = this.props.reportToCreate;
      let cloned = { ...commentToCreate, comment: selectedComment };
      this.props.updateReport(cloned);*/
      let perem = event.target.value;
      let lState = this.state.day.slice();
      lState[Id].comment = perem;
      this.setState({ day: lState });
    }

    handleProjectChange(event) {
      let perem = event.target.value;
      this.setState({ projectId: perem });
      this.getFeaturesByProjectId(perem);
    }

    handleFeatureChange(event) {
      let perem = event.target.value;
      this.setState({ featureId: perem });
      this.getTaskByFeatureId(perem);
    }
    handleTaskChange(event) {
      let perem = event.target.value;
      this.setState({ taskId: perem });
    }
  
    inputHour(event, Id) {
      /*let selectedHour = +timeTask.target.value;
      console.log(+timeTask.target.value);
      this.setState({ timeTask: selectedHour });*/
      let perem = event.target.value;
      if (Number.isNaN(perem) || perem < 0 || perem > 24) {
        return;
      }
      let lState = this.state.day.slice();
      lState[Id].hours = perem;
      this.setState({ day: lState });
    }
    inputWorkUnits(event, Id) {
      /*let selectedHour = +timeTask.target.value;
      console.log(+timeTask.target.value);
      this.setState({ timeTask: selectedHour });*/
      let perem = event.target.value;
      if (Number.isNaN(perem) || perem < 0 || perem > 24) {
        return;
      }
      let lState = this.state.day.slice();
      lState[Id].workUnits = perem;
      this.setState({ day: lState });
    }
    postReport = (event) => {
      /*
      {
  "comment": "string",
  "date": "2020-03-11T16:05:37.661Z",
  "factor": {
    "id": 0
  },
  "feature": {
    "featureId": 0
  },
  "hours": 0,
  "project": {
    "projectID": 0
  },
  "reportId": 0,
  "status": "string",
  "task": {
    "id": 0
  },
  "user": {
    "userId": 0
  },
  "workUnits": 0
}
      */
     if (!(this.state.day[0].comment === "" || this.state.day[0].factorId === "" || this.state.day[0].hours === 0)){
      let cloned1 = { date: this.state.blocksDate[0], comment: this.state.day[0].comment, factor: {id: this.state.day[0].factorId}, feature: {featureId: this.state.featureId}, project: {projectID: this.state.projectId}, task: {id: this.state.taskId}, status: "confirmed", user: {userId: this.state.userId}, workUnits: this.state.day[0].workUnits, hours: this.state.day[0].hours };
      
      console.log(cloned1);
      console.dir(this.state);
  
      CreateReportService.post("/report", cloned1, {
        headers: {
          "Content-Type": "application/json"
        }
      })
        .then(response => {
          console.dir(response);
          //window.confirm("Отчет за Понедельник был отправлен");
          //this.setState({validReport: valid});
        })
        .catch(error => {
          console.error(error);
          alert("Заполните все поля!");
        });}
        if (!(this.state.day[1].comment === "" || this.state.day[1].factorId === "" || this.state.day[1].hours === 0)){
          let cloned2 = { date: this.state.blocksDate[1], comment: this.state.day[1].comment, factor: {id: this.state.day[1].factorId}, feature: {featureId: this.state.featureId}, project: {projectID: this.state.projectId}, task: {id: this.state.taskId}, status: "confirmed", user: {userId: this.state.userId}, workUnits: this.state.day[1].workUnits, hours: this.state.day[1].hours };
          CreateReportService.post("/report", cloned2, {
            headers: {
              "Content-Type": "application/json"
            }
          })
            .then(response => {
              console.dir(response);
              //window.confirm("Отчет за Вторник был отправлен");
              //this.setState({validReport: valid});
            })
            .catch(error => {
              console.error(error);
              alert("Заполните все поля!");
            });
        }
        if (!(this.state.day[2].comment === "" || this.state.day[2].factorId === "" || this.state.day[1].hours === 0)){
          let cloned3 = { date: this.state.blocksDate[2], comment: this.state.day[2].comment, factor: {id: this.state.day[2].factorId}, feature: {featureId: this.state.featureId}, project: {projectID: this.state.projectId}, task: {id: this.state.taskId}, status: "confirmed", user: {userId: this.state.userId}, workUnits: this.state.day[2].workUnits, hours: this.state.day[2].hours };
          CreateReportService.post("/report", cloned3, {
            headers: {
              "Content-Type": "application/json"
            }
          })
            .then(response => {
              console.dir(response);
              //window.confirm("Отчет за Среду был отправлен");
              //this.setState({validReport: valid});
            })
            .catch(error => {
              console.error(error);
              alert("Заполните все поля!");
            });
        }
        if (!(this.state.day[3].comment === "" || this.state.day[3].factorId === "" || this.state.day[1].hours === 0)){
          let cloned4 = { date: this.state.blocksDate[3], comment: this.state.day[3].comment, factor: {id: this.state.day[3].factorId}, feature: {featureId: this.state.featureId}, project: {projectID: this.state.projectId}, task: {id: this.state.taskId}, status: "confirmed", user: {userId: this.state.userId}, workUnits: this.state.day[3].workUnits, hours: this.state.day[3].hours };
          CreateReportService.post("/report", cloned4, {
            headers: {
              "Content-Type": "application/json"
            }
          })
            .then(response => {
              console.dir(response);
              //window.confirm("Отчет за Четверг был отправлен");
              //this.setState({validReport: valid});
            })
            .catch(error => {
              console.error(error);
              alert("Заполните все поля!");
            });
        }
        if (!(this.state.day[4].comment === "" || this.state.day[4].factorId === "" || this.state.day[1].hours === 0)){
          let cloned5 = { date: this.state.blocksDate[4], comment: this.state.day[4].comment, factor: {id: this.state.day[4].factorId}, feature: {featureId: this.state.featureId}, project: {projectID: this.state.projectId}, task: {id: this.state.taskId}, status: "confirmed", user: {userId: this.state.userId}, workUnits: this.state.day[4].workUnits, hours: this.state.day[4].hours };
          CreateReportService.post("/report", cloned5, {
            headers: {
              "Content-Type": "application/json"
            }
          })
            .then(response => {
              console.dir(response);
              //window.confirm("Отчет за Пятницу был отправлен");
              //this.setState({validReport: valid});
            })
            .catch(error => {
              console.error(error);
              alert("Заполните все поля!");
            });
        }
        if (!(this.state.day[5].comment === "" || this.state.day[5].factorId === "" || this.state.day[1].hours === 0)){
          let cloned6 = { date: this.state.blocksDate[5], comment: this.state.day[5].comment, factor: {id: this.state.day[5].factorId}, feature: {featureId: this.state.featureId}, project: {projectID: this.state.projectId}, task: {id: this.state.taskId}, status: "confirmed", user: {userId: this.state.userId}, workUnits: this.state.day[5].workUnits, hours: this.state.day[5].hours };
          CreateReportService.post("/report", cloned6, {
            headers: {
              "Content-Type": "application/json"
            }
          })
            .then(response => {
              console.dir(response);
              //window.confirm("Отчет за Субботу был отправлен");
              //this.setState({validReport: valid});
            })
            .catch(error => {
              console.error(error);
              alert("Заполните все поля!");
            });
        }
        if (!(this.state.day[6].comment === "" || this.state.day[6].factorId === "" || this.state.day[1].hours === 0)){
          let cloned7 = { date: this.state.blocksDate[6], comment: this.state.day[6].comment, factor: {id: this.state.day[6].factorId}, feature: {featureId: this.state.featureId}, project: {projectID: this.state.projectId}, task: {id: this.state.taskId}, status: "confirmed", user: {userId: this.state.userId}, workUnits: this.state.day[6].workUnits, hours: this.state.day[6].hours };
          CreateReportService.post("/report", cloned7, {
            headers: {
              "Content-Type": "application/json"
            }
          })
            .then(response => {
              console.dir(response);
              //window.confirm("Отчет за Воскресенье был отправлен.");
              //this.setState({validReport: valid});
            })
            .catch(error => {
              console.error(error);
              alert("Заполните все поля!");
            });
        }
        window.confirm("Данные успешно отправлены.");
    }

  render() {
    const { day, factor, projects, features, tasks} = this.state;
    let daysline = this.state.blocks.map((item, index) => {
    return <th className={s.th} key={index}><p>{item}</p>
    <p>{(this.state.blocksDate[index])}</p>
      </th>;
    });
    let hoursline = this.state.blocks.map((item,index) => {
      return <td className={s.form}><form><p><input type="number" min="0" max="12" step="1" value={day[index].hours}
      onChange={(e) => (this.inputHour(e,index))}/></p></form></td>;
    });
    let workUnitsline = this.state.blocks.map((item,index) => {
      return <td className={s.form}><form><p><input type="number" min="0" max="12" step="1" value={day[index].workUnits}
      onChange={(e) => (this.inputWorkUnits(e,index))}/></p></form></td>;
    });
    let commentline = this.state.blocks.map((item, index) => {
      return <td className={s.form}><form><p><input className={s.form1} value={day[index].comment}
      onChange={(e) => (this.handleChangeComment(e,index))}>
        </input>
        </p></form>
        </td>;
    });
    let factorline = this.state.blocks.map((item, index) => {
      return <td className={s.form}>
        <form><p>
          <select value = {day[index].factorId}  onChange={(e)=>(this.handleChange(e,index))}>
          {factor.map(factor => (<option value={factor.id}>{factor.name}</option>))}
          </select>
        </p></form>
      </td>;
    });
    let addline = this.state.blocks.map(() => {
      return <td className={s.form}><button className="Add Hours " type="Add Hours">Add Hours</button></td>;
    });
    console.dir("LOOOK");
    console.dir(this.state.projects);
    return (
      <div >
        <Header />
        <Menu />
          <table className={s.table}>
          <p className={s.p}>Project <select value = {this.state.projectID} onChange={this.handleProjectChange} >
            <option>project</option>
                {projects.map(project => (<option value={project.projectID}>
                    {project.projectName}</option>
                ))}
              </select></p>

            <p className={s.p}>Feature <select value = {this.state.featureId} onChange={this.handleFeatureChange}>
              <option>feature</option>
              {features.map(feature => (<option value={feature.featureId} key={feature.featureId}>
                    {feature.name}</option>
                ))}
            </select></p>
            <p className={s.p}>Task <select value = {this.state.taskId} onChange={this.handleTaskChange}>
                <option>task</option>
                {tasks.map(task => (<option value={task.id} key={task.id}>
                    {task.name}</option>
                ))}
              </select></p>

            <tr>
              <th className={s.th}><p>Day</p></th>
              {daysline}
            </tr>
            <tr>
              <th className={s.th}><p>Hours</p></th>
              {hoursline}
            </tr>
            <tr>
              <th className={s.th}><p>Work Units</p></th>
              {workUnitsline}
            </tr>
            <tr>
              <th className={s.th}><p>Factor</p></th>
              {factorline}
            </tr>
            <tr>
              <th className={s.th}><p>Comment</p></th>
              {commentline}
            </tr>
            <tr>
              <th className={s.th}><p></p></th>
              {addline}
            </tr>
          </table>
          <button className={s.btn} type="submit" onClick={this.postReport}>Save report(s)</button>
        <Footer />
      </div>
    );
  }
}
export default EditReportforweek;
