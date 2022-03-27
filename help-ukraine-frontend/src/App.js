import './App.css';
import React  from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import RolePicker from "./screens/RolePicker";
import Refugee from "./screens/Refugee";
import Volunteer from "./screens/Volunteer";
import Login from "./screens/Login";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Login />}>
              </Route>
              <Route path="/rolePicker" element={<RolePicker />}>
              </Route>
              <Route path="/volunteer" element={<Volunteer />}>
              </Route>
              <Route path="/refugee" element={<Refugee />}>
              </Route>

          </Routes>
      </BrowserRouter>
  );
}

export default App;
