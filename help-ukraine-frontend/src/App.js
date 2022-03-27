import './App.css';
import React  from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import RolePicker from "./components/RolePicker";

function App() {

  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<RolePicker />}>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
