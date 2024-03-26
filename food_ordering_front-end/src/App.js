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
import Order from './body/Order.js';
import axios from 'axios';

axios.defaults.withCredentials=true; //預設withCredentials為true，不加此預設在axios方法呼叫有可能失效

function App() {

  //使用者資料
  const [userData, setUserData] = useState(null);


  useEffect(() => {
    axios.get(`http://localhost:8080/userController/getSession`, {
      withCredentials: true // 使請求攜帶 cookies
    }).then(response => {
      setUserData(response.data);
    })
      .catch(error => {
        if (error.response) {
          if (error.response.status === 400) {
            // 處理400錯誤，例如顯示一個錯誤消息給用戶
            console.log(error.response.data)
          }
        } else {
          console.error('Error:', error.message);
          // 其他錯誤情況
          //登出
          axios.put(`http://localhost:8080/userController/logout`, {
          }).then(response => {
            console.log(response.data)
          })
            .catch(error => {
              console.error('Error updating user profile:', error);
            });
        }
      });
  }, []);

  return (
    <Router>
      <Header setUserData={setUserData} userData={userData} />
      <Routes>
        <Route path="/" element={<Meals userData={userData} />} />
        {userData != null && <Route path="/updataUser" element={<UpdataUser setUserData={setUserData} userData={userData} />} />}
        <Route path='/cart' element={<Cart userData={userData} />} />
        <Route path='/order' element={<Order userData={userData}/>}/>
      </Routes>
    </Router>
  );
}

export default App;
