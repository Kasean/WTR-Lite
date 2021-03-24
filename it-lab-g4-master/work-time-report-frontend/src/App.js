import React, { Component } from "react";
import Login from "./components/loginPage/Login";
import { Route, Switch, Redirect, withRouter } from "react-router-dom";
import CreateReportPage from "./components/CreateReportPage/CreateReportPage"
import page3 from "./components/EditReportforweek/EditReportforweek";
import MyReports from "./components/MyReports/MyReports";

class App extends Component {
  render() {
    const { history } = this.props;

    return (
      <div className="App">
        <Switch>
          <Route history={history} path="/login" component={Login} />
          <Route history={history} path="/createReportPage" component={CreateReportPage} />
          <Route history={history} path="/editReportforweek" component={page3} />
          <Route history={history} path="/myReports" component={MyReports} />
          <Redirect from="/" to="/login" />
        </Switch>
      </div>
    );
  }
}

export default withRouter(App);
