import logo from './logo.svg';
import React, { useState, useEffect } from 'react';
import './App.css';
import Header from './header/Header.js';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
// import { useEffect } from 'react';

function App() {

  //帳號密碼

  const [userData, setUserData] = useState();

  return (
    <div>
      <div><Header setUserData={setUserData} /></div>
    </div>
  );
}

export default App;
