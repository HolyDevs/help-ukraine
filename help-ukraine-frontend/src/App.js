import './App.css';
import React from 'react';
import {Routes, Route, HashRouter} from "react-router-dom";
import RolePicker from "./screens/register/RolePicker";
import Login from "./screens/tabs/Login";
import Error from "./screens/tabs/Error";
import Family from "./screens/tabs/refugee/Family";
import Profile from "./screens/tabs/Profile";
import Search from "./screens/tabs/refugee/Search";
import Refugee from './screens/tabs/refugee/Refugee';
import RegisterRefugeeBasicInfoForm from "./screens/register/refugee/RegisterRefugeeBasicInfoForm";
import RegisterHost from "./screens/register/host/RegisterHost";
import RegisterRefugeeAccountForm from "./screens/register/refugee/RegisterRefugeeAccountForm";
import RegisterRefugeeFurtherForm from "./screens/register/refugee/RegisterRefugeeFurtherForm";
import Details from './screens/tabs/Details';
import RegisterHostFurtherForm from "./screens/register/host/RegisterHostFurtherForm";
import Host from "./screens/tabs/host/Host";
import Offers from "./screens/tabs/host/Offers";
import HostDetails from "./screens/tabs/HostDetails";


function App() {

    return (
        <HashRouter>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/rolePicker" element={<RolePicker/>}/>
                <Route path="/host" element={<Host/>}>
                    <Route path="profile" element={<Profile/>}/>
                    <Route path="offers" element={<Offers/>}/>
                    <Route path="offers/:id" element={<HostDetails/>}/>
                </Route>
                <Route path="/refugee" element={<Refugee/>}>
                    <Route path="family" element={<Family/>}/>
                    <Route path="profile" element={<Profile/>}/>
                    <Route path="search" element={<Search/>}/>
                    <Route path="search/:id" element={<Details/>}/>
                </Route>
                <Route path="/error" element={<Error/>}/>
                <Route path="/registerRefugee/account-creation" element={<RegisterRefugeeAccountForm/>}/>
                <Route path="/registerRefugee/account-basic-info" element={<RegisterRefugeeBasicInfoForm/>}/>
                <Route path="/registerRefugee/account-further-info" element={<RegisterRefugeeFurtherForm/>}/>
                <Route path="/registerHost" element={<RegisterHost/>}/>
                <Route path="/registerHost/host-further-info" element={<RegisterHostFurtherForm/>}/>
            </Routes>
        </HashRouter>
    );
}

export default App;
