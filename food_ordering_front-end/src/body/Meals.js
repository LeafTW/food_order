import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navs from './Navs.js';

const Meals = (props) => {
    //(api findAllByItem)回傳page
    const [MealsPage, setMealsPage] = useState(null);
    //(api findItemCountByMeals) 回傳的Item
    const [mealsItem, setMealsItem] = useState(null);
    // 當前頁面
    const [currentPage, setCurrentPage] = useState(0);
    // 當前 導覽與頁籤內容(按鈕)的位置
    const [ItemPage, setItemPage] = useState('z');
    //會員使用者名稱
    const { username } = props.userData ?? {};
    // 購物車post內容
    const [cartPost, setCartPost] = useState({
        quantity: "1"
        ,username: null
    });

    const cartPostChange = (event, key, value) => {
        //username預設為null，username重新賦值
        if(cartPost.username == null){
            setCartPost(cartData => ({
                ...cartData,
                username: username
            }));
        }
        if (event) {
            // 防止a標籤Route行為
            event.preventDefault();
        }
        setCartPost(cartData => ({
            ...cartData,
            [key]: value
        }));
    };

    {/* 新增購物車 post*/ }
    useEffect((event) => {
        // console.log("cart POST : "+`${username.username}`)
        if (cartPost.name != null) {
            axios.post("http://localhost:8080/mealsList/insertIntoCart", cartPost)
                .then(response => {
                    // console.log(response)
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }

    }, [cartPost]);

    //取得餐點分類
    useEffect(() => {
        let url = `http://localhost:8080/mealsList/findItemCountByMeals`;
        axios.get(url)
            .then(response => {
                // console.log(response)
                setMealsItem(response.data);
                // 设置 ItemPage 的初始值为第一个元素的 item 值
                if (response.data && response.data.length > 0) {
                    setItemPage(response.data[0].item);
                }
            });
    }, []);

    // 取得餐點列表
    useEffect(() => {
        let url = `http://localhost:8080/mealsList/findAllByItem/${currentPage}/${ItemPage}`;
        axios.get(url)
            .then(response => {
                // console.log(response)'
                setMealsPage(response.data);
                // setMealsPage(response.data);
            });
    }, [currentPage, ItemPage]);

    return (
        <div>
            <Navs mealsItem={mealsItem} MealsPage={MealsPage} setCurrentPage={setCurrentPage} setItemPage={setItemPage} cartPostChange={cartPostChange} />
            {/* {console.log(`${username}`)} */}
        </div>
    );
}

export default Meals;
