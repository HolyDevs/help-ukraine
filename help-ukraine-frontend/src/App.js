import './App.css';
import React from 'react';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import RolePicker from "./screens/RolePicker";
import Volunteer from "./screens/Volunteer";
import Login from "./screens/Login";
import Error from "./screens/Error";
import Family from "./screens/Family";
import Profile from "./screens/Profile";
import Search from "./screens/Search";
import Main from './screens/Main';
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
                <Route path="/main" element={<Main/>}>
                    <Route path="family" element={<Family/>}/>
                    <Route path="profile" element={<Profile/>}/>
                    <Route path="search" element={<Search/>}/>
                </Route>
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
