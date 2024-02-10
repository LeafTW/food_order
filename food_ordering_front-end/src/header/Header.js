import React, { useState } from 'react';
import Login from '../Login.js';
import './Header.css';


const Toolbar = ({setUserData}) => {
 
    return (
        <div>
            <div className="toolbar">
                {/* 商家logo */}
                <img src="images/Leaf_Logo.png" alt="商家logo" className="logo" />

                {/* 首頁、購物車超連結 */}
                <nav className="navbar">
                    <ul className="nav-links">
                        <li className="nav-item"><a href="/" className="nav-link">首頁</a></li>
                        <li className="nav-item"><a href="/cart" className="nav-link">購物車</a></li>
                    </ul>
                </nav>

                {/* 登入會員功能 */}
                {/* <div className="login">
                <button className="login-button" onClick={handleLoginClick}>登入</button>
            </div> */}

                {/* 登入會員彈跳視窗 */}
                {/* <!-- Button trigger modal --> */}
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#UserModal">
                    登入
                </button>
            </div>

            <div><Login setUserData={setUserData}/></div>

        </div>

    );
}

export default Toolbar;