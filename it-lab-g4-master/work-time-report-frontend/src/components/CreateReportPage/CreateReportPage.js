import React, { Component } from "react";
import Header from "./../Header/Header";
import Menu from "./../MenuPage/MenuPage";
import Footer from "./../Footer/Footer";
import CreateReportTable from "../VievAssistants/table/VievAssistants.js";
import Button from "./../VievAssistants/button/button";
import cre from "./CreateReportPage.module.css";
import CreateReportService from "../../services/CreateReportService";
import { connect } from "react-redux";
import * as selectActions from "../../store/actions/createReport-actions";

class CreateReportPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      date: new Date(),
      reportToCrate: {}
    };
    this.date = new Date();


    this.handlerPrevDay = this.handlerPrevDay.bind(this);
  }

  handlerPrevDay() {
    let reportToCreate = this.props.reportToCrate;
    console.log(this.props.reportToCreate);
    this.date.setDate(this.date.getDate() - 1);
    this.setState(this.date);
    console.log(this.props);
    let cloned = { ...reportToCreate, date: this.date.toISOString() };
    this.props.updateReport(cloned);
  }

  handlerNextDay = () => {
    let reportToCreate = this.props.reportToCrate;
    console.log(this.props.reportToCreate);
    this.date.setDate(this.date.getDate() + 1);
    this.setState(this.date);
    console.log(this.props);
    let cloned = { ...reportToCreate, date: this.date.toISOString() };
    this.props.updateReport(cloned);
  }
  render() {
    let { users } = this.state;
    return (
      <div class="page">
        <Header />
        <Menu />
        <nav className="dayReport">
          <p className={cre.text}>
            {" "}
            Report of: ....................... Дата заполнения отчета:{" "}
            {this.date.toLocaleDateString()}{" "}
          </p>
          <button type="submit" calssName={cre.button} onClick={this.handlerPrevDay}> Prev Day</button>
          <button type="submit" calssName={cre.button} onClick={this.handlerNextDay}> Next Day</button>
        </nav>

        <table>
          <tr className="firstRows">
            <td className={cre.table}>project/Activity</td>
            <td className={cre.table}>Feature</td>
            <td className={cre.table}>Task</td>
            <td className={cre.table}>Hours</td>
            <td className={cre.table}>Work Units</td>
            <td className={cre.table}>Factor</td>
            <td className={cre.table}>Comments</td>
          </tr>
        </table>
        <CreateReportTable />
        <Footer />

      </div>
    );
  }
}
const mapStateToProps = state => {
  const {
    date,
    reportToCreate
  } = state.CreateReportReducer;
  return {
    date,
    reportToCreate
  };
};
export default connect(mapStateToProps, selectActions)(CreateReportPage);
