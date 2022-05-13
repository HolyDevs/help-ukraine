import React from "react";
import { Outlet } from "react-router";
import "../../../styles/Main.scss";
import HostTabbar from "../../../components/HostTabbar";

const Host = () => {

    return (
        <div className="main">
            <div className="main__content">
                <Outlet/>
            </div>
            <HostTabbar/>
        </div>
    )
}

export default Host;