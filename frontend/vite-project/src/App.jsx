//import { useState } from 'react'
/*
import './App.css'

function App() {
  return (
    <div>
      <h1>Task Management Project</h1>
      <p>Frontend setup is working successfully 🚀</p>
    </div>
  )
}

export default App
*/
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<h1>Dashboard Coming Soon 🚀</h1>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

