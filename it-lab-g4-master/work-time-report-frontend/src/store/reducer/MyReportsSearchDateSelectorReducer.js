import * as types from "../actions/action-types";

const initialState = {
  startDay: "",
  lastDay: ""
};

const selectDayReducer = (state = initialState, action) => {
  switch (action.type) {
    case types.SELECT_START_DAY:
      return Object.assign({}, state, { startDay: action.startDay });
    case types.SELECT_LAST_DAY:
      return Object.assign({}, state, { lastDay: action.lastDay });
    default:
      return state;
  }
};

export default selectDayReducer;
