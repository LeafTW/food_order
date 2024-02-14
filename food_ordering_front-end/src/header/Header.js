import React, { useState, useEffect } from 'react';
import Login from '../Login.js';
import './Header.css';


const Toolbar = ({ setUserData ,userData}) => {


    const logout = () => {
        // 清除用户数据或执行其他登出操作
        setUserData(null);
    };


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
                    </ul>
                </nav>

                {/* 判斷是否登入顯示 登入/用戶資訊 */}
                {userData != null ? (
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            你好~ {userData.name} 
                        </button>
                           
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a class="dropdown-item" href="#">訂單</a></li>
                            <li><a class="dropdown-item" href="#/updataUser">資本資料</a></li>
                            <li><a class="dropdown-item"  href="#" onClick={logout}>登出</a></li>
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