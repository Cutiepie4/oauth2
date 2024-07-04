
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './component/Home';
import Login from './component/Login';

const App = () => {
  return (
    <Routes>
      <Route path="/login/oauth" element={<Home />} />
      <Route index element={<Login />} />
    </Routes>
  );
};

export default App;
