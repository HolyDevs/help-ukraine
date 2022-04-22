import './App.css';
import React from 'react';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import RolePicker from "./screens/RolePicker";
import Refugee from "./screens/Refugee";
import Volunteer from "./screens/Volunteer";
import Login from "./screens/Login";
import Error from "./screens/Error";
import Family from "./screens/Family";
import Profile from "./screens/Profile";
import Search from "./screens/Search";
import Main from './screens/Main';


function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />}/> //To correct
                <Route path="/rolePicker" element={<RolePicker/>}/>
                <Route path="/volunteer" element={<Volunteer/>}/>
                <Route path="/refugee" element={<Refugee/>}/>
                <Route path="/main" element={<Main/>}>
                    <Route path="family" element={<Family/>}/>
                    <Route path="profile" element={<Profile/>}/>
                    <Route path="search" element={<Search/>}/>
                </Route>
                <Route path="/error" element={<Error/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
