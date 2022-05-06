import React from "react";
import { Outlet } from "react-router";
import Tabbar from "../../components/Tabbar";
import "../../styles/Main.scss";

const Main = () => {

    return (
        <div className="main">
            <div className="main__content">
                <Outlet/>
            </div>
            <Tabbar/>
        </div>
    )
}

export default Main;