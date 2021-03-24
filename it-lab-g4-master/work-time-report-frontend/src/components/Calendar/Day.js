import React from "react";

class Day extends React.Component {
  render() {
    const {
      day,
      day: { date, isCurrentMonth, isToday, number },
      select,
    } = this.props;
    return (
      <span
        key={date.toString()}
        className={
          "day" +
          (isToday ? " today" : "") +
          (isCurrentMonth ? "" : " different-month")
        }
        onClick={() => select(day)}
      >
        {number}
      </span>
    );
  }
}

export default Day;
