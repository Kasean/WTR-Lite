import axios from "axios";

export class ReportService {
  get(url) {
    return axios.get(process.env.REACT_APP_AXIOS_BASE_URL + url);
  }

  post(url, report) {
    return axios.post(process.env.REACT_APP_AXIOS_BASE_URL + url, report);
  }
}

export default new ReportService();
