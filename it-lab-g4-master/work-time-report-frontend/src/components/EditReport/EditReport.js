import React from "react";
import s from "./EditReport.module.css";
import CalendarButton from "../CalendarButton/CalendarButton";

class EditReport extends React.Component {
  render() {
    return (
      <div>
        <div className={s.editReport}>
          <h1>EditReport:</h1>
          <table className={s.table}>
            <tr>
              <th className={s.th}>
                <p>For day:</p>
              </th>
              <th className={s.form}>
                <CalendarButton />
              </th>
              <th className={s.th}>
                <p>For Week:</p>
              </th>
              <th className={s.form}>
                <CalendarButton />
              </th>
            </tr>
          </table>
        </div>
      </div>
    );
  }
}
export default EditReport;
