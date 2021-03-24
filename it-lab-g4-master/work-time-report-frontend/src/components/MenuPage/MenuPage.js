import React from "react";
import "./MenuPage.css";
import { Link } from "react-router-dom";
import { weekNumber } from '../../functions/scripts.js';

class Menu extends React.Component {
  render() {
    return (
      <div>
        <ul className="top-bar">
          <li>
            <Link className="menuButton" to="/myReports">
              My Reports
            </Link>
          </li>
          <li>
            <Link className="menuButton" >
              Viev Assistants
            </Link>
          </li>
          <li>
            <Link className="menuButton" >
              Help
            </Link>
          </li>
          <li>
            <Link className="menuButton" to="/login">
              Logout
            </Link>
          </li>
          <li>
            <Link className="menuButton" to="/createReportPage">
              Create Report Page
            </Link>
          </li>
          <li>
          <Link className="menuButton" to={{
        	  pathname: '/editReportforweek',
        	  state: {
              fromNotifications: weekNumber(),
              userId: 1
        	  }
        	}}>Create Report On Current Week</Link>
          </li>          
        </ul>
      </div>
    );
  }
}
export default Menu;
