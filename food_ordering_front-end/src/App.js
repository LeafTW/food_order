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
import AllMeals from './body/AllMeals.js';
import { QueryClient, QueryClientProvider } from 'react-query';
import Cookies from 'universal-cookie';

//========================= cookie ===================
const cookies = new Cookies();
export const getAuthToken = () => {
  if (cookies.get('token')===undefined){
    return '';
  }
  return cookies.get('token');
  };
//============================================

axios.defaults.withCredentials=true; //預設withCredentials為true，不加此預設在axios方法呼叫有可能失效
const baseUrl = process.env.REACT_APP_API_BASE_URL; // 獲取環境變量中的基礎 URL

// 創建一個 QueryClient 實例
const queryClient = new QueryClient();

function App() {

  //使用者資料
  const [userData, setUserData] = useState(null);


  useEffect(() => {
    // axios.get(`${baseUrl}/userController/getSession`, {
      const token = getAuthToken();
      console.log(`App.js ${token}`);
      axios.get(`${baseUrl}/userController/getToken`, {
        headers: {
          'Authorization': `Bearer ${token}`
        },
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
          axios.put(`${baseUrl}/userController/logout`, {
            withCredentials: true // 使請求攜帶 cookies
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
    
    <QueryClientProvider client={queryClient}>
      <Router>
        <Header setUserData={setUserData} userData={userData} />
        <Routes>
          <Route path="/" element={<Meals userData={userData} />} />
          {userData != null && <Route path="/updataUser" element={<UpdataUser setUserData={setUserData} userData={userData} />} />}
          <Route path='/cart' element={<Cart userData={userData} />} />
          <Route path='/order' element={<Order userData={userData} />} />
          <Route path='/allMeals' element={<AllMeals userData={userData} />} />
        </Routes>
      </Router>
    </QueryClientProvider>
    
  );
}

export default App;
