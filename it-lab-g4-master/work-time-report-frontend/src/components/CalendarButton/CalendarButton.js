import React from "react";
import ReactDOM from "react-dom";
import Calendar from "../Calendar/Calendar";
import s from "./CalendarButton.module.css";
import moment from "moment";

class CalendarButton extends React.Component {
  constructor(props) {
    super(props);
    this.dates = new Date();
    this.dateFull = this.dates.toLocaleDateString();
    this.state = {
      isCalendarShow: false,
      defaultDay: this.dateFull,
      month: moment(),
      selected: moment()
        .startOf("day")
        .format("YYYY-MM-DD")
    };
    this.onChangeDate = this.onChangeDate.bind(this);
    this.handleChangeForm = this.handleChangeForm.bind(this);
    this.handleClickOutside = this.handleClickOutside.bind(this);
    this.handleChangeForm= this.handleChangeForm.bind(this);
  }
  onChangeDate(day) {
    this.props.onSelectDay(day.date.format("YYYY-MM-DD"));
    this.setState({
      selected: day.date.format("YYYY-MM-DD"),
      month: day.date.clone(),
      isCalendarShow: false
    });
  }

  inputStateHandler = () => {
    this.setState({
      isCalendarShow: !this.state.isCalendarShow
    });
  };

  handleChangeForm(event) {
    this.props.onInputDay(event.target.value)
    this.setState({
      selected: event.target.value
    });
  }

  componentWillUnmount() {
    document.removeEventListener("click", this.handleClickOutside, false);
  }

  componentWillMount() {
    document.addEventListener("click", this.handleClickOutside, false);
  }

  handleClickOutside(event) {
    const domNode = ReactDOM.findDOMNode(this);
    if (!domNode || !domNode.contains(event.target)) {
      this.setState({
        isCalendarShow: false
      });
    }
  }

  render() {
    return (
      <div>
        <form className={s.calendarForm}>
          <input
            type="textarea"
            value={this.state.selected}
            onChange={this.handleChangeForm}
            className={s.textarea}
            maxLength="10"
            pattern="[0-9]{4}-[0-1][0-9]-[0-3][0-9]"
          />

          <input
            type="button"
            className={s.button}
            onClick={this.inputStateHandler}
          />

          {this.state.isCalendarShow ? (
            <Calendar
              month={this.state.month}
              selected={this.state.selected}
              onChangeDate={this.onChangeDate}
            />
          ) : null}
        </form>
      </div>
    );
  }
}

export default CalendarButton;
