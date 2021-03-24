import axios from "axios";

export class SearchReportService {
  get(url) {
    return axios.get(process.env.REACT_APP_AXIOS_BASE_URL + url);
  }

  post(url, searchReport) {
    return axios.post(process.env.REACT_APP_AXIOS_BASE_URL + url, searchReport);
  }
}

export default new SearchReportService();
