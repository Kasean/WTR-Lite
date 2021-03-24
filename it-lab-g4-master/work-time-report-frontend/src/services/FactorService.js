import axios from "axios";

export class FactorService {
  get(url) {
    return axios.get(process.env.REACT_APP_AXIOS_BASE_URL + url);
  }

  post(url) {
    return axios.post(process.env.REACT_APP_AXIOS_BASE_URL + url);
  }
}

export default new FactorService();
