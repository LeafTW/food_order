import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Card from './Card.js';
const ShopList = () => {
    // 當前頁面
    const [currentPage, setCurrentPage] = useState(0);
    //response回傳page
    const [MealsPage, setMealsPage] = useState(null);
    //response回傳mealsList的Item
    const[mealsItem,setMealsItem]=useState(null);
    //解析MealsPage
    const { content, first, last, number, totalPages } = MealsPage ?? {};
    //分頁資訊圖示
    const pageItems = [];

    const{id,item_Count,item_name}=ItemContent || {};

    // 取得餐點列表
    useEffect(() => {
        let url = `http://localhost:8080/mealsList/getAllMeals/${currentPage}`;
        axios.get(url)
            .then(response => {
                console.log(response)
                setMealsPage(response.data);
                // setMealsPage(response.data);
            });
    }, [currentPage]);

    const clickPage = (event, pageIndex) => {
        // 防止a標籤Route行為
        event.preventDefault();
        // 直接將currentPage設定為指定頁面，當頁useEffect偵測到currentPage改變時，會重新取得資料
        setCurrentPage(pageIndex)
    }


    //分頁資訊圖示
    for (let index = 0; index < totalPages; index += 1) {
        pageItems.push(
            <li key={index} className={`page-item ${index === number && "active"}`}>
                <a className="page-link" href={`#${index}`} onClick={(event) => clickPage(event, index)}>{index + 1}</a>
            </li>
        )
    }

    for(let index=0; index<)

    useEffect(() => {
        let url = `http://localhost:8080/mealsList/findItemCountByItem`;
        axios.get(url)
            .then(response => {
                console.log(response)
                setMealsItem(response.data);
            });
    }, []);

    return (
        <div>
            <nav>
                <div className="nav nav-tabs" id="nav-tab" role="tablist">
                    <button className="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button" role="tab" aria-controls="nav-home" aria-selected="true">Home</button>
                    <button className="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button" role="tab" aria-controls="nav-profile" aria-selected="false">Profile</button>
                    <button className="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-contact" type="button" role="tab" aria-controls="nav-contact" aria-selected="false">Contact</button>
                </div>
            </nav>
            <div className="tab-content" id="nav-tabContent">

                <div className="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                    {/* 分頁清單 */}
                    <div className='row'>
                        {content && content.map(tour => <Card tour={tour} />)}
                    </div>

                    {/* 分頁資訊 */}
                    <div className="d-flex justify-content-center">
                        <nav aria-label="Page navigation example ">
                            <ul className="pagination">
                                <li className={`"page-item ${first && "disabled"}`}>
                                    <a className="page-link" href="#" onClick={(event) => clickPage(event, number - 1)}>上一頁</a>
                                </li>
                                {pageItems}
                                <li className={`"page-item ${last && "disabled"}`}>
                                    <a className="page-link" href="#" onClick={(event) => clickPage(event, number + 1)}>下一頁</a>
                                </li>
                            </ul>
                        </nav>
                    </div>

                </div>

                <div className="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                    
                </div>
                <div className="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">{first}</div>
            </div>
        </div>
    );
}

export default ShopList;
