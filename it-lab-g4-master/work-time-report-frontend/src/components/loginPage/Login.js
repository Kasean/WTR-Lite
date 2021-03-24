import React, { Component} from 'react';
import {Link} from "react-router-dom";
import { FormErrors } from './FormErrors';
import './Form.css';

class Login extends Component {
  constructor (props) {
    super(props);
    this.state = {
      login: '',
      password: '',
      // formErrors: {email: '', password: ''},
      // emailValid: false,
      // passwordValid: false,
      // formValid: false
      //user:{}
    }
  }

  handleUserLoginInput = (e) => {
    const value = e.target.value;
    this.setState({login: value});
    console.log(value);

    // const value = e.target.value;
    // this.setState({[name]: value},
    //               () => { this.validateField(name, value) });
  }

  handleUserPasswordInput = (e) => {
    const value = e.target.value;
    this.setState({password: value})


  }

  loginInUser(){
    alert("Добро пожаловать!")
    
  }
  // validateField(fieldName, value) {
  //   let fieldValidationErrors = this.state.formErrors;
  //   let emailValid = this.state.emailValid;
  //   let passwordValid = this.state.passwordValid;

  //   switch(fieldName) {
  //     case 'email':
  //       emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
  //       fieldValidationErrors.email = emailValid ? '' : ' is invalid';
  //       break;
  //     case 'password':
  //       passwordValid = value.length >= 6;
  //       fieldValidationErrors.password = passwordValid ? '': ' is too short';
  //       break;
  //     default:
  //       break;
  //   }
  //   this.setState({formErrors: fieldValidationErrors,
  //                   emailValid: emailValid,
  //                   passwordValid: passwordValid
  //                 }, this.validateForm);
  // }

  // validateForm() {
  //   this.setState({formValid: this.state.emailValid && this.state.passwordValid});
  //   console.log(this.state.formValid);
  // }

  // errorClass(error) {
  //   return(error.length === 0 ? '' : 'has-error');
  // }

  render () {
    return (
      <form className="demoForm">
        <h1>Авторизация</h1>
        
        <div className="form-group">
          <input type="text" required className="form-control" 
            placeholder="login"
            value={this.state.email}
            onChange={this.handleUserLoginInput}  />
        </div>
        <div className="form-group">
          <input type="password" className="form-control" name="password"
            placeholder="password"
            value={this.state.password}
            onChange={this.handleUserPasswordInput}  />
        </div>
        <Link to = "/myReports"><button className="btn btn-primary">Log in</button></Link>
        {/* <div className="panel panel-default">
          <FormErrors formErrors={this.state.formErrors} />
        </div> */}
      </form>
    )
  }
}

export default Login;
