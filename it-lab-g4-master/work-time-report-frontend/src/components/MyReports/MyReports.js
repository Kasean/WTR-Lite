import React from "react";
// import EditReport from "../EditReport/EditReport";
import SearchReport from "../SearchReport/SearchReport";
import ReportTable from "../ReportTable/ReportTable";
import Header from "../Header/Header";
import Menu from "../MenuPage/MenuPage";
import Footer from "../Footer/Footer";

class MyReports extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <Header />
        <Menu />
        {/* <EditReport /> */}
        <SearchReport />
        <ReportTable />
        <Footer />
      </div>
    );
  }
}
export default MyReports;
