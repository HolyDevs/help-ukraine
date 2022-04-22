import './App.css';
import React from 'react';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import RolePicker from "./screens/RolePicker";
import Refugee from "./screens/Refugee";
import Volunteer from "./screens/Volunteer";
import Login from "./screens/Login";
import Error from "./screens/Error";
import RegisterRefugeeBasicInfoForm from "./screens/register/refugee/RegisterRefugeeBasicInfoForm";
import RegisterVolunteer from "./screens/register/volunteer/RegisterVolunteer";
import RegisterRefugeeAccountForm from "./screens/register/refugee/RegisterRefugeeAccountForm";
import RegisterRefugeeFurtherForm from "./screens/register/refugee/RegisterRefugeeFurtherForm";


function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />}/>
                <Route path="/rolePicker" element={<RolePicker/>}/>
                <Route path="/volunteer" element={<Volunteer/>}/>
                <Route path="/refugee" element={<Refugee/>}/>
                <Route path="/error" element={<Error/>}/>
                <Route path="/registerRefugee/account-creation" element={<RegisterRefugeeAccountForm/>}/>
                <Route path="/registerRefugee/account-basic-info" element={<RegisterRefugeeBasicInfoForm/>}/>
                <Route path="/registerRefugee/account-further-info" element={<RegisterRefugeeFurtherForm/>}/>
                <Route path="/registerVolunteer" element={<RegisterVolunteer/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
