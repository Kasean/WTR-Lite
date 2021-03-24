import React from "react";
import VievAssistants from "../table/VievAssistants.js"

class Button extends React.Component {
  state = {
    count: 0
  };

  inputStateHandler = () => {
    this.setState({
      count: this.state.count + 1
    });
  };

  render() {
    return (
      <div>
        {" "}
        {[...Array(this.state.count)].map(() => (
          <VievAssistants />
        ))}
        <button
          type="submit"
          className="addRow"
          onClick={this.inputStateHandler}
        >
          Add row
        </button>
      </div>
    );
  }
}
export default Button;
