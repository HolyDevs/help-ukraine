import React from "react";
import {Outlet} from "react-router";
import RefugeeTabbar from "../../../components/RefugeeTabbar";
import "../../../styles/Main.scss";

const Refugee = () => {

    return (
        <div className="main">
            <div className="main__content">
                <Outlet/>
            </div>
            <RefugeeTabbar/>
        </div>
    )
}

export default Refugee;