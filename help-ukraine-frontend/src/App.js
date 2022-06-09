import './App.css';
import React from 'react';
import {HashRouter, Route, Routes} from "react-router-dom";
import RolePicker from "./screens/register/RolePicker";
import Login from "./screens/tabs/Login";
import Error from "./screens/tabs/Error";
import Family from "./screens/tabs/refugee/Family";
import Search from "./screens/tabs/refugee/Search";
import Refugee from './screens/tabs/refugee/Refugee';
import RegisterHost from "./screens/register/host/RegisterHost";
import RegisterRefugeeAccountForm from "./screens/register/refugee/RegisterRefugeeAccountForm";
import RegisterRefugeeFurtherForm from "./screens/register/refugee/RegisterRefugeeFurtherForm";
import SearchOfferDetails from './screens/tabs/refugee/SearchOfferDetails';
import RegisterHostFurtherForm from "./screens/register/host/RegisterHostFurtherForm";
import Host from "./screens/tabs/host/Host";
import Offers from "./screens/tabs/host/Offers";
import HostOfferDetails from "./screens/tabs/host/HostOfferDetails";
import RegisterRefugeeFamilyMembersForm from "./screens/register/refugee/RegisterRefugeeFamilyMembersForm";
import HostProfile from "./screens/tabs/host/HostProfile";
import RefugeeProfile from "./screens/tabs/refugee/RefugeeProfile";
import HostRequests from './screens/tabs/host/HostRequests';


function App() {

    return (
        <HashRouter>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/rolePicker" element={<RolePicker/>}/>
                <Route path="/host" element={<Host/>}>
                    <Route path="profile" element={<HostProfile/>}/>
                    <Route path="offers" element={<Offers/>}/>
                    <Route path="offers/:id" element={<HostOfferDetails/>}/>
                    <Route path="offers/create" element={<HostOfferDetails/>}/>
                    <Route path="requests" element={<HostRequests/>}/>
                </Route>
                <Route path="/refugee" element={<Refugee/>}>
                    <Route path="family" element={<Family/>}/>
                    <Route path="profile" element={<RefugeeProfile/>}/>
                    <Route path="search" element={<Search/>}/>
                    <Route path="search/:id" element={<SearchOfferDetails/>}/>
                </Route>
                <Route path="/error" element={<Error/>}/>
                <Route path="/registerRefugee/account-creation" element={<RegisterRefugeeAccountForm/>}/>
                <Route path="/registerRefugee/account-family" element={<RegisterRefugeeFamilyMembersForm/>}/>
                <Route path="/registerRefugee/account-further-info" element={<RegisterRefugeeFurtherForm/>}/>
                <Route path="/registerHost" element={<RegisterHost/>}/>
                <Route path="/registerHost/host-further-info" element={<RegisterHostFurtherForm/>}/>
            </Routes>
        </HashRouter>
    );
}

export default App;
