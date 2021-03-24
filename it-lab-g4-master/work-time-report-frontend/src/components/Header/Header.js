import React from "react";
import "./Header.css";
import LogoEpol from "./LogoEpol.jpg";

function Header() {
  return (
    <header exact className="active" to="/">
      <div className="logo">
        <img src={LogoEpol} alt="Error" />
      </div>
      <div className="logoText">
        <p> Work time reports</p>
      </div>
    </header>
  );
}
export default Header;
