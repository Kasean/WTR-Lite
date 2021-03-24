import axios from "axios";

export class BookService {
  get(url) {
    return axios.get(process.env.REACT_APP_AXIOS_BASE_URL + url);
  }

  post(url, book) {
    return axios.post(process.env.REACT_APP_AXIOS_BASE_URL + url, book);
  }
}

export default new BookService();
