import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Card from './Card.js';
import Navs from './Navs.js';

const ShopList = () => {
    //(api findAllByItem)回傳page
    const [MealsPage, setMealsPage] = useState(null);
    //(api findItemCountByMeals) 回傳的Item
    const [mealsItem, setMealsItem] = useState(null);
    // 當前頁面
    const [currentPage, setCurrentPage] = useState(0);
    // 當前 導覽與頁籤內容(按鈕)的位置
    const [ItemPage, setItemPage] = useState('z');



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
                // console.log(response)
                setMealsPage(response.data);
                // setMealsPage(response.data);
            });
    }, [currentPage, ItemPage]);

    return (
        <div>
            <Navs mealsItem={mealsItem} MealsPage={MealsPage} setCurrentPage={setCurrentPage} setItemPage={setItemPage} />
        </div>
    );
}

export default ShopList;
