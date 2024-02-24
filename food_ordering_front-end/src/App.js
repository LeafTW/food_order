import logo from './logo.svg';
import React, { useState, useEffect } from 'react';
import { HashRouter as Router, Route, Routes } from 'react-router-dom'
import './App.css';
import Header from './header/Header.js';
import UpdataUser from './body/UpdataUser.js';
import ShopList from './body/ShopList.js';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
// import { useEffect } from 'react';

function App() {

  //帳號密碼

  const [userData, setUserData] = useState(null);

  useEffect(() => {
    console.log("userData value:", userData);
  }, [userData]);

  return (
    <Router>
      <Header setUserData={setUserData} userData={userData} />
      <Routes>
        <Route path="/" element={<ShopList/>}/>
        {userData != null && <Route path="/updataUser" element= {<UpdataUser setUserData={setUserData} userData={userData} />} />}
      </Routes>
    </Router>
  );
}

export default App;
