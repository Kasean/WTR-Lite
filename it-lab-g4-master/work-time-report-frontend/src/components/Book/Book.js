import React, { Component } from "react";
import bookService from "../../services/BookService";
import Header from "./../Header/Header";
import Menu from "./../MenuPage/MenuPage";
import Footer from "./../Footer/Footer";


export default class Book extends Component {
  constructor(props) {
    super(props);
    this.state = {
      books: [],
      name: ""
    };
    this.onSubmit = this.onSubmit.bind(this);
    this.onChangeName = this.onChangeName.bind(this);
  }

  componentDidMount() {
    bookService
      .get("/book")
      .then(response => {
        console.dir(response);
        this.setState({ books: response.data });
      })
      .catch(error => {
        console.error(error);
        alert("Ошибка получения книг.");
      });
  }

  onSubmit(event) {
    event.preventDefault();
    if (this.state.name !== "") {
      bookService
        .post("/book", { name: this.state.name })
        .then(response => {
          console.dir(response);
          const clonedBooks = this.state.books.slice();
          clonedBooks.push(response.data);
          this.setState({ books: clonedBooks, name: "" });
        })
        .catch(error => {
          console.error(error);
          alert("Ошибка получения книг.");
        });
    } else {
      console.error("Empty name");
    }
  }

  onChangeName(event) {
    this.setState({ name: event.target.value });
  }

  render() {
    const { books, name } = this.state;
    return (
      <div>
        <Header />
        <Menu />
        <ol>
          {books.map(book => (
            <li key={book.id}>{book.name}</li>
          ))}
        </ol>
        <br />
        {name !== "" && <span> Введённое имя: {name}</span>}
        <form onSubmit={this.onSubmit}>
          <p>
            <label>
              {" "}
              Имя:{" "}
              <input
                type="text"
                name="name"
                value={this.state.name}
                onChange={this.onChangeName}
                required
                minLength="1"
              />
            </label>
          </p>
          <p>
            <input type="submit" value="Добавить книгу" />
          </p>
        </form>
        <Footer />
      </div>
    );
  }
}
