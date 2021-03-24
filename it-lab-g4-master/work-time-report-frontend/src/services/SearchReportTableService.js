import axios from "axios";

export class  SearchReportById {
  get(url) {
    return axios.get(process.env.REACT_APP_AXIOS_BASE_URL + url);
  }

  post(url, searchReportById,getFeatureReportsById) {
    return axios.post(process.env.REACT_APP_AXIOS_BASE_URL + url,  searchReportById, getFeatureReportsById);
  }

  }


export default new  SearchReportById()