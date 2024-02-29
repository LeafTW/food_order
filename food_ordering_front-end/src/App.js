import logo from './logo.svg';
import React, { useState, useEffect } from 'react';
import { HashRouter as Router, Route, Routes } from 'react-router-dom'
import './App.css';
import Header from './header/Header.js';
import UpdataUser from './body/UpdataUser.js';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import Meals from './body/Meals.js';
import Cart from './body/Cart.js';

function App() {

  //使用者資料
  const [userData, setUserData] = useState(null);


  // useEffect(() => {
  //   console.log("userData value:", userData);
  // }, [userData]);

  return (
    <Router>
      <Header setUserData={setUserData} userData={userData} />
      <Routes>
        <Route path="/" element={<Meals userData={userData}/>}/>
        {userData != null && <Route path="/updataUser" element= {<UpdataUser setUserData={setUserData} userData={userData} />} />}
        <Route path='/cart' element={<Cart userData={userData}/>}/>
      </Routes>
    </Router>
  );
}

export default App;
