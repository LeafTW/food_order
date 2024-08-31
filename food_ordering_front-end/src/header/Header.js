import React, { useState, useEffect } from 'react';
import Login from '../Login.js';
import './Header.css';
import axios from 'axios';
import Cookies from 'universal-cookie';

axios.defaults.withCredentials=true; //預設withCredentials為true，不加此預設在axios方法呼叫有可能失效
const baseUrl = process.env.REACT_APP_API_BASE_URL; // 獲取環境變量中的基礎 URL

const Toolbar = ({ setUserData, userData }) => {

    const cookies = new Cookies();

    const logout = () => {
    cookies.remove('token', { path: '/' }); // 假設你的 token 存儲在名為 'token' 的 cookie 中
    // 可以選擇性的移除其他相關的 cookies
    // cookies.remove('anotherCookie', { path: '/' });
    
    // 可以進行其他登出邏輯，如重定向到登錄頁面
    window.location.href = '/login'; // 假設你的登錄頁面路徑是 '/login'
    };

    // const logout = () => {
    //     // 清除用户数据或执行其他登出操作
    //     setUserData(null);
    //     axios.put(`${baseUrl}/userController/logout`, {
    //         withCredentials: true // 使請求攜帶 cookies
    //     }).then(response => {
    //        console.log(response.data)
    //     })
    //         .catch(error => {
    //             console.error('Error updating user profile:', error);
    //         });
    // };

    return (
        <div>
            <div className="toolbar">
                {/* 商家logo */}
                <img src="images/Leaf_Logo.png" alt="商家logo" className="logo" />

                {/* 首頁、購物車超連結 */}
                <nav className="navbar">
                    <ul className="nav-links">
                        <li className="nav-item"><a href="#/" className="nav-link">首頁</a></li>
                        <li className="nav-item"><a href="#/cart" className="nav-link">購物車</a></li>
                        <li className="nav-item"><a href="#/order" className="nav-link">訂單</a></li>
                        <li className="nav-item"><a href="#/allMeals" className="nav-link">所有餐單</a></li>
                    </ul>
                </nav>

                {/* 判斷是否登入顯示 登入/用戶資訊 */}
                {userData != null ? (
                    <div className="dropdown">
                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            你好~ {userData.name}
                        </button>

                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a className="dropdown-item" href="#/order">訂單</a></li>
                            <li><a className="dropdown-item" href="#/updataUser">資本資料</a></li>
                            <li><a className="dropdown-item" href="#" onClick={logout}>登出</a></li>
                        </ul>
                    </div>
                ) : (
                    <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#UserModal">
                        登入
                    </button>
                )}
            </div>

            {/* 登入會員彈跳視窗 */}
            <div><Login setUserData={setUserData} /></div>
        </div>

    );
}

export default Toolbar;