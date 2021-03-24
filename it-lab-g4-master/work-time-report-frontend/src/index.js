import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";

import { BrowserRouter } from "react-router-dom";
import { createBrowserHistory } from "history";

import App from "./App";
import  {store} from "./store/store";
import { Provider } from "react-redux";




const history = createBrowserHistory();

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter basename={"/lab-g4"} history={history}>
      <App />
    </BrowserRouter>
  </Provider>,
  document.getElementById("root")
);
serviceWorker.unregister();
