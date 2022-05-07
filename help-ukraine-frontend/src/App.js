import './App.css';
import React from 'react';
import {Routes, Route, HashRouter} from "react-router-dom";
import RolePicker from "./screens/RolePicker";
import Volunteer from "./screens/Volunteer";
import Login from "./screens/Login";
import Error from "./screens/Error";
import Family from "./screens/tabs/Family";
import Profile from "./screens/tabs/Profile";
import Search from "./screens/tabs/Search";
import Main from './screens/tabs/Main';
import RegisterRefugeeBasicInfoForm from "./screens/register/refugee/RegisterRefugeeBasicInfoForm";
import RegisterVolunteer from "./screens/register/volunteer/RegisterVolunteer";
import RegisterRefugeeAccountForm from "./screens/register/refugee/RegisterRefugeeAccountForm";
import RegisterRefugeeFurtherForm from "./screens/register/refugee/RegisterRefugeeFurtherForm";
import Details from './screens/tabs/Details';
import RegisterVolunteerFurtherForm from "./screens/register/volunteer/RegisterVolunteerFurtherForm";


function App() {

    return (
        <HashRouter>
            <Routes>
                <Route path="/" element={<Login />}/>
                <Route path="/rolePicker" element={<RolePicker/>}/>
                <Route path="/volunteer" element={<Volunteer/>}/>
                <Route path="/main" element={<Main/>}>
                    <Route path="family" element={<Family/>}/>
                    <Route path="profile" element={<Profile/>}/>
                    <Route path="search" element={<Search/>}/>
                    <Route path="search/:id" element={<Details/>}/>
                </Route>
                <Route path="/error" element={<Error/>}/>
                <Route path="/registerRefugee/account-creation" element={<RegisterRefugeeAccountForm/>}/>
                <Route path="/registerRefugee/account-basic-info" element={<RegisterRefugeeBasicInfoForm/>}/>
                <Route path="/registerRefugee/account-further-info" element={<RegisterRefugeeFurtherForm/>}/>
                <Route path="/registerVolunteer" element={<RegisterVolunteer/>}/>
                <Route path="/registerVolunteer/volunteer-further-info" element={<RegisterVolunteerFurtherForm/>}/>
            </Routes>
        </HashRouter>
    );
}

export default App;
