import React from "react";
import s from "./ReportTable.module.css";
import { connect } from "react-redux";
import moment from "moment";

class ReportTable extends React.Component {
  constructor(props) {
    super(props);
    this.dates = moment()
      .startOf("day")
      .format("YYYY-MM-DD");
  }

  render() {
    const { reports, lastDay, startDay } = this.props;
    return (
      <div className={s.contentTable}>
        <h3 className={s.drawingDates}>
          Search from{" "}
          <span className={s.Selection}>
            {startDay ? startDay : this.dates}
          </span>{" "}
          to{" "}
          <span className={s.Selection}>{lastDay ? lastDay : this.dates}</span>
        </h3>
        <table className={s.table}>
          <tr className={s.trOne}>
            <td className={s.td}>Project/activity</td>
            <td className={s.td}>Feature</td>
            <td className={s.td}>Task</td>
            <td className={s.td}>Hours</td> <td className={s.td}>Work units</td>
            <td className={s.td}>Factor</td> <td className={s.td}>Comment</td>
            <td className={s.td}>Status</td>
          </tr>
          {reports.map(report => (
            <tr className={s.tr} key={report.reportId}>
              <td className={s.td} key={report.project.projectId}>
                {report.project.projectName}
              </td>
              <td className={s.td} key={report.feature.featureId}>
                {report.feature.name}
              </td>
              { report.task ? (
              <td className={s.td} key={report.task.id}>
                {report.task.name}
              </td>
                ) : (
              <td className={s.td}>
              </td>
              ) }
              <td className={s.td}>{report.hours}</td>
              <td className={s.td}>{report.workUnits}</td>
              <td className={s.td} key={report.factor.Id}>
                {report.factor.name}
              </td>
              <td className={s.td}>{report.comment}</td>
              <td className={s.td}>{report.status}</td>          
            </tr>
          ))}
        </table>
      </div>
    );
  }
}
const mapStateToProps = state => {
  const { reports } = state.valueReducer;
  const { lastDay, startDay } = state.selectedDay;
  console.dir(state);
  return {
    reports,
    lastDay,
    startDay
  };
};

export default connect(mapStateToProps)(ReportTable);
