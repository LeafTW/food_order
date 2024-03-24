import React, { Component, useState, useEffect } from 'react'
import axios from 'axios';
import "./css/Order.css"

const Order = (props) => {
  //api 訂單列表變數
  const [orderPage, setorderPage] = useState();
  const { content, first, last, number, totalPages } = orderPage ?? {};
  //頁籤 變數
  const pageItems = [];
  // 當前頁面
  const [currentPage, setCurrentPage] = useState(0);
  //父類 會員資料
  const { username } = props.userData ?? { username: "null" };

  const [searchTerm, setSearchTerm] = useState("");

  // 取得訂單列表
  useEffect(() => {
    let url = `http://localhost:8080/orderController/totalOrderGet/${currentPage}/${username}`;
    axios.post(url,{searchTerm})
      .then(response => {
        // console.log(response.data);
        setorderPage(response.data);
        // setMealsPage(response.data);
      });
  }, [currentPage,searchTerm,username]);

  const clickPage = (event, pageIndex) => {
    // 防止a標籤Route行為
    event.preventDefault();
    // 直接將currentPage設定為指定頁面，當頁useEffect偵測到currentPage改變時，會重新取得資料
    setCurrentPage(pageIndex);
  }


  //分頁資訊圖示
  for (let index = 0; index < totalPages; index += 1) {
    pageItems.push(
      <li key={index} className={`page-item ${index === number && "active"}`}>
        <a className="page-link" href={`#${index}`} onClick={(event) => clickPage(event, index)}>{index + 1}</a>
      </li>
    )
  }

  //頁籤內容
  const createNavShow = () => {
    return (
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
    )
  }

  // 定義搜尋函式
  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const createOrderTable = (contentData, index) => {
    // 將 ISO 格式的日期轉換為 Date 物件
    const orderDate = new Date(contentData.date);
    // 格式化日期
    const formattedDate = `${orderDate.getFullYear()}-${(orderDate.getMonth() + 1).toString().padStart(2, '0')}-${orderDate.getDate().toString().padStart(2, '0')} ${orderDate.getHours().toString().padStart(2, '0')}:${orderDate.getMinutes().toString().padStart(2, '0')}:${orderDate.getSeconds().toString().padStart(2, '0')}`;

    return (
      <tr key={index}>
        <th scope="row">{index}</th>
        <td>{contentData.name}</td>
        <td>{contentData.totalprice}</td>
        <td>{contentData.state}</td>
        <td>{contentData.orderitem}</td>
        <td>{formattedDate}</td>
      </tr>
    );
  };

  return (
    <div className="container">
      <div className="search-bar">
        Name Search: 
        <input type="text" className="search-input" placeholder="輸入搜尋字串" value={searchTerm} onChange={handleSearch} />
      </div>
      <div className="table-responsive">
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col">訂單內容</th>
              <th scope="col">總價</th>
              <th scope="col">狀態</th>
              <th scope="col">訂單編號</th>
              <th scope="col">訂購日</th>
            </tr>
          </thead>
          <tbody>
            {content && content.map((contentData, index) => createOrderTable(contentData, index))}
          </tbody>
        </table>
      </div>

      <div className="tab-content" id="nav-tabContent">
        {createNavShow()}
      </div>
 
    </div>

  )

}

export default Order