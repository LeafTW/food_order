import React, { Component, useState } from 'react'
import Card from './Card';

const Navs = (props) => {

    //解析page
    const { content, first, last, number, totalPages } = props.MealsPage ?? {};


    const pageItems = [];

    const clickPage = (event, pageIndex) => {
        // 防止a標籤Route行為
        event.preventDefault();
        // 直接將currentPage設定為指定頁面，當頁useEffect偵測到currentPage改變時，會重新取得資料
        props.setCurrentPage(pageIndex);
    }

    const clickItemPage = (event, item) => {
        // 防止a標籤Route行為
        event.preventDefault();
        // 直接將setItemPage設定為指定頁面，當頁useEffect偵測到setItemPage改變時，會重新取得資料
        props.setItemPage(item);
        props.setCurrentPage(0);
    }

    //分頁資訊圖示
    for (let index = 0; index < totalPages; index += 1) {
        pageItems.push(
            <li key={index} className={`page-item ${index === number && "active"}`}>
                <a className="page-link" href={`#${index}`} onClick={(event) => clickPage(event, index)}>{index + 1}</a>
            </li>
        )
    }
    //導覽與頁籤內容(按鈕)
    const createNavButton = (ItemData, index) => {
        return (
            <button className={`nav-link ${index === 0 && "active"}`} id={`nav-${ItemData.item}`} key={index} data-bs-toggle="tab" data-bs-target={`#target_${ItemData.item}`}
                type="button" role="tab" onClick={(event) => clickItemPage(event,ItemData.item)}>{ItemData.item_name}</button>
        )
    }

    //導覽與頁籤內容()
    const createNavShow = (ItemData, index) => {
        // console.log(props.MealsPage.content.name);
        return (
            <div className={`tab-pane fade show ${index === 0 && "active"}`} id={`target_${ItemData.item}`} key={index} role="tabpanel" aria-labelledby="nav-home-tab">
                {/* 分頁清單 */}
                <div className='row'>
                    {content && content.map(cardData => <Card key={cardData.id} cardData={cardData}  cartPostChange={props.cartPostChange}/>)}
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
        )
    }

    return (
        <div>
            <nav>
                <div className="nav nav-tabs" id="nav-tab" role="tablist">
                    {props.mealsItem && props.mealsItem.map((ItemData, index) => createNavButton(ItemData, index))}
                </div>
            </nav>
            <div className="tab-content" id="nav-tabContent">
                {props.mealsItem && props.mealsItem.map((ItemData, index) => createNavShow(ItemData, index))}
            </div>
        </div>
    );
}

export default Navs