import React, { useState, useEffect } from 'react';
import axios from 'axios';


const Login = ({ setUserData }) => {

    {/* 各項數值*/ }
    const [useData, setConstUserData] = useState({
        username: "",
        password: "",
        name: "",
        email: "",
        phone: ""
    })
    {/* 登入狀態變數*/ }
    const [loginError, setLoginError] = useState(false);
    {/* 新增會員狀態變數*/ }
    const [addError, setaddError] = useState(false);

    {/* 輸入值 錯誤 狀態變數*/ }
    const [formErrors, setFormErrors] = useState({
        username: false,
        password: false,
        name: false,
        email: false,
        phone: false
    });

    {/* 偵測到input更新變數*/ }
    const inputUserData = (e) => {
        const { name, value } = e.target;
        if (value === "") {
            setFormErrors({
                ...formErrors,
                [name]: true
            })
        } else {
            setFormErrors({
                ...formErrors,
                [name]: false
            })
        }
        setConstUserData(
            {
                ...useData,
                [name]: value
            }
        )
    }

     {/* 輸入錯誤值全部設為false*/ }
    const clearformErrors = () => {
        setFormErrors({
            username: false
        })
    }

    {/* 登入會員 post*/ }
    const LoginUserAction = () => {
        setLoginError(false);
        axios.post("http://localhost:8080/userController/queryUser", {
            username: `${useData.username}`,
            password: `${useData.password}`
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
    {/* 新增會員 post*/ }
    const addUserAction = () => {
        setaddError(false);
        console.log(useData)
        axios.post("http://localhost:8080/userController/addUser", useData)
            .then(response => {
                console.log(response)
                if (response.data == false) {
                    setaddError(true);// 新增會員失败，设置状态为true
                }
            })
            .catch(error => {
                console.error('Error:', error);
                setaddError(true); // 新增會員失败，设置状态为true
            });
    }

    {/* <!-- Modal --> */ }
    {/* 登入彈跳視窗*/ }
    return (
        <div>
            <div class="modal fade  " id="UserModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="UserModalLabel">會員登入</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="mb-3">
                                    <label for="username" class="form-label">帳號 :</label>
                                    <input type="text" class="form-control" id="username" name="username" onChange={(e) => inputUserData(e)} />
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">密碼 :</label>
                                    <input type="password" class="form-control" id="password" name="password" onChange={(e) => inputUserData(e)} />
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#addUserModal" onClick={clearformErrors}>註冊</button>
                            <button type="button" class="btn btn-primary" onClick={LoginUserAction} data-bs-toggle="modal" data-bs-target="#SuccessModal" >登入</button>
                        </div>
                    </div>
                </div>
            </div>
            {/* 登入後 彈跳視窗 登入成功/失敗 */}
            <div class="modal fade  " id="SuccessModal" tabindex="-1" aria-hidden="true" >
                <div class="modal-dialog modal-dialog-centered">
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
            {/* 註冊 彈跳視窗*/}
            <div class="modal fade " id="addUserModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="UserModalLabel">註冊會員</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={clearformErrors}></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="mb-3">
                                    <label for="name" class="form-label">名字 :</label>
                                    <input type="text" class="form-control" id="name" name="name" onChange={(e) => inputUserData(e)} />
                                    {formErrors.name && <p className="text-danger">名字不能為空</p>}
                                </div>
                                <div class="mb-3">
                                    <label for="username" class="form-label">帳號 :</label>
                                    <input type="text" class="form-control" id="username" name="username" onChange={(e) => inputUserData(e)} />
                                    {formErrors.username && <p className="text-danger">帳號不能為空</p>}
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">密碼 :</label>
                                    <input type="password" class="form-control" id="password" name="password" onChange={(e) => inputUserData(e)} />
                                    {formErrors.password && <p className="text-danger">密碼不能為空</p>}
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">電子郵件 :</label>
                                    <input type="text" class="form-control" id="email" name="email" onChange={(e) => inputUserData(e)} />
                                    {formErrors.email && <p className="text-danger">電子郵件不能為空</p>}
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">電話 :</label>
                                    <input type="text" class="form-control" id="phone" name="phone" onChange={(e) => inputUserData(e)} />
                                    {formErrors.phone && <p className="text-danger">電話不能為空</p>}
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" onClick={addUserAction} data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#addSuccessModal">送出</button>
                        </div>
                    </div>
                </div>
            </div>

            {/* 新增會員後 彈跳視窗 成功/失敗 */}
            <div class="modal fade  " id="addSuccessModal" tabindex="-1" aria-hidden="true" >
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-body">
                            {addError == false && <p>註冊成功，請重新登入</p>}
                            {addError == true && <p>註冊失敗，帳號重複</p>}
                        </div>
                        <div class="modal-footer">
                            {addError == true &&
                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#addUserModal">OK</button>
                            }
                            {addError == false &&
                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#UserModal">OK</button>
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login;