import axios from "axios";

export class CreateReportService {
  get(url, project) {
    return axios.get(process.env.REACT_APP_AXIOS_BASE_URL + url);
  }

  post(url, project, feature, task) {
    return axios.post(
      process.env.REACT_APP_AXIOS_BASE_URL + url,
      project,
      feature,
      task
    );
  }
}

export default new CreateReportService();
