import React, { useState, useEffect } from 'react';
import axios from 'axios';

axios.defaults.withCredentials=true; //預設withCredentials為true，不加此預設在axios方法呼叫有可能失效
const baseUrl = process.env.REACT_APP_API_BASE_URL; // 獲取環境變量中的基礎 URL

const updataUser = ({ setUserData, userData }) => {
    {/* 偵測到input更新變數*/ }
    const handleChange = e => {
        const { name, value } = e.target;
        setUserData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    {/* 刪除會員 get*/ }
    const deleteUser =()=>{
        setUserData(null);
        axios.get(`${baseUrl}/userController/deleteUser/${userData.username}`,{
            withCredentials: true
        })
            .catch(error => {
                console.error('Error updating user profile:', error);
            });
    }
    {/* 更新會員 post*/ }
    const handleSubmit = e => {
        e.preventDefault();
        axios.post('${baseUrl}/userController/updateUser', userData)
            .then(response => {
                // console.log('User profile updated successfully:', response);
                setUserData(response.data)
            })
            .catch(error => {
                console.error('Error updating user profile:', error);
            });
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4">用户資料</h2>
            {/* 更新用護表格 */}
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">姓名：</label>
                    <input type="text" className="form-control" id="name" name="name" value={userData.name} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="username" className="form-label">用戶名：</label>
                    <input type="text" className="form-control" id="username" name="username" value={userData.username} placeholder="Disabled input here..." disabled />
                    <span id="passwordHelpInline" className="form-text"> 用戶名不可更改</span>
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">密码：</label>
                    <input type="password" className="form-control" id="password" name="password" value={userData.password} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">E-mail：</label>
                    <input type="email" className="form-control" id="email" name="email" value={userData.email} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="phone" className="form-label">電話：</label>
                    <input type="tel" className="form-control" id="phone" name="phone" value={userData.phone} onChange={handleChange} />
                </div>
                <button type="submit" className="btn btn-primary">更新资料</button>
                <button type="button" className="btn btn-danger" onClick={deleteUser} >刪除會員</button>
            </form>
        </div>
    );
}

export default updataUser;
