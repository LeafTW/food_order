import React, { useState, useEffect } from 'react';
import axios from 'axios';


const Login = ({ setUserData }) => {

    const [useData,setConstUserData]=useData({
        username: "",
        password: "",
        name: "",
        email: "",
        phone: ""
    })

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loginError, setLoginError] = useState(false);

    const inputUserData=(e)=>{
        const {name , value} =e.target;
        setConstUserData(
            {
                [name]:value
            }
        )
    }
    const addUserAction = () => {
        setLoginError(false);
        axios.post("http://localhost:8080/userController/queryUser", {
            username: `${username}`,
            password: `${password}`
        })
            .then(response => {

                if (response.data !== "") {
                    setUserData(response.data);
                    console.log(response);
                } else {
                    setLoginError(true);// 登入失败，设置状态为true
                }
            })
            .catch(error => {
                console.error('Error:', error);
                setLoginError(true); // 登入失败，设置状态为true
            });
    }

    {/* <!-- Modal --> */ }
    {/* 登入彈跳視窗*/ }
    return (
        <div>
            <div class="modal fade modal-dialog modal-dialog-centered" id="UserModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="UserModalLabel">會員登入</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="mb-3">
                                    <label for="username" class="form-label">帳號 :</label>
                                    <input type="text" class="form-control" id="username" name="username" onChange={(e) => setUsername(e.target.value)} />
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">密碼 :</label>
                                    <input type="password" class="form-control" id="password" name="password" onChange={(e) => setPassword(e.target.value)} />
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#addUserModal">註冊</button>
                            <button type="button" class="btn btn-primary" onClick={addUserAction} data-bs-toggle="modal" data-bs-target="#SuccessModal" >登入</button>
                        </div>
                    </div>
                </div>
            </div>
            {/* 登入後 彈跳視窗 登入成功/失敗 */}
            <div class="modal fade modal-dialog modal-dialog-centered" id="SuccessModal" tabindex="-1" aria-hidden="true" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                            {loginError == false && <p>登入成功</p>}
                            {loginError == true && <p>登入失敗，請檢查您的帳號和密碼</p>}
                        </div>
                        <div class="modal-footer">
                            {loginError == true &&
                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#UserModal">OK</button>
                            }
                            {loginError == false &&
                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">OK</button>
                            }
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade modal-dialog modal-dialog-centered" id="addUserModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="UserModalLabel">註冊會員</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="mb-3">
                                    <label for="name" class="form-label">名字 :</label>
                                    <input type="text" class="form-control" id="name" name="name" onChange={(e) => setUsername(e.target.value)} />
                                </div>
                                <div class="mb-3">
                                    <label for="username" class="form-label">帳號 :</label>
                                    <input type="text" class="form-control" id="username" name="username" onChange={(e) => setPassword(e.target.value)} />
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">密碼 :</label>
                                    <input type="password" class="form-control" id="password" name="password" onChange={(e) => setPassword(e.target.value)} />
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">電子郵件 :</label>
                                    <input type="password" class="form-control" id="email" name="email" onChange={(e) => setPassword(e.target.value)} />
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">電話 :</label>
                                    <input type="password" class="form-control" id="phone" name="phone" onChange={(e) => setPassword(e.target.value)} />
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" onClick={addUserAction} data-bs-toggle="modal" data-bs-target="#SuccessModal" >送出</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login;