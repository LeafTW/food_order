import React, { Component, useState, useEffect } from 'react'
import { useQuery } from 'react-query';
import axios from 'axios';
import "./css/Order.css"
import Cookies from 'universal-cookie';

axios.defaults.withCredentials=true; //預設withCredentials為true，不加此預設在axios方法呼叫有可能失效
const baseUrl = process.env.REACT_APP_API_BASE_URL; // 獲取環境變量中的基礎 URL

//========================= cookie ===================
const cookies = new Cookies();
export const getAuthToken = () => {
  if (cookies.get('token')===undefined){
    return '';
  }
  return cookies.get('token');
  };
//============================================

 //========================= 使用 useQuery 取得訂單列表====================
const fetchOrders =async(currentPage,searchTerm,username) =>{
  const url=`${baseUrl}/orderController/totalOrderGet/${currentPage}/${username}`;
  const token = getAuthToken();
  console.log(`Bearer ${token}`);
  const config = {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  };
  const response = await axios.post(url, { searchTerm }, config);
  console.log(response.data);
  return response.data;
}

const downloadPDF = async (username) => {
  const url = `${baseUrl}/pdf/download/jasperreports/${username}`;
  // 發送 GET 請求至伺服器，並指定回應類型為 'blob'
  const response = await axios.get(url, { responseType: 'blob' });
  // 將伺服器回應的 blob 資料轉換為 URL
  const urlBlob = window.URL.createObjectURL(new Blob([response.data]));
  // 創建一個臨時的 <a> 標籤
  const link = document.createElement('a');
  // 設置 <a> 標籤的 href 屬性為之前創建的 blob URL
  link.href = urlBlob;
  // 設置下載的文件名為 'orders.pdf'
  link.setAttribute('download', 'orders.pdf');
  // 將 <a> 標籤添加到 DOM 中
  document.body.appendChild(link);
  // 觸發點擊事件以啟動文件下載
  link.click();
  // 從 DOM 中移除臨時的 <a> 標籤
  link.parentNode.removeChild(link);
};


  //======================================================================

const Order = (props) => {
  //api 訂單列表變數
  // const [orderPage, setorderPage] = useState();
  // const { content, first, last, number, totalPages } = orderPage ?? {};
  //頁籤 變數
  const pageItems = [];
  // 當前頁面
  const [currentPage, setCurrentPage] = useState(0);
  //父類 會員資料
  const { username } = props.userData ?? { username: "null" };

  const [searchTerm, setSearchTerm] = useState("");

  //========================= 使用 useQuery 取得訂單列表====================
     // 使用 useQuery 取得訂單列表
     const { data: orderPage, error, isLoading } = useQuery(
      ['orders', currentPage, username, searchTerm],
      () => fetchOrders(currentPage,searchTerm,username),
      {
        keepPreviousData: true,
      }
    );
  
    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    const { content, first, last, number, totalPages } = orderPage ?? {};
  //======================================================================

  
  // 取得訂單列表
  /*
  useEffect(() => {
    let url = `${baseUrl}/orderController/totalOrderGet/${currentPage}/${username}`;
    axios.post(url,{searchTerm})
      .then(response => {
        // console.log(response.data);
        setorderPage(response.data);
        // setMealsPage(response.data);
      });
  }, [currentPage,searchTerm,username]);
  */

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
        <td>{contentData.totalquantity}</td>
        <td>{contentData.totalprice}</td>
        <td>{contentData.state}</td>
        <td>{contentData.orderitem}</td>
        <td>{formattedDate}</td>
      </tr>
    );
  };

  return (
    <div className="container">
     <div className="search-bar d-flex justify-content-between align-items-center">
        <div className="flex-grow-1">
          訂單內容: 
          <input type="text" className="search-input" placeholder="輸入搜尋字串" value={searchTerm} onChange={handleSearch} />
        </div>
        <button onClick={() => downloadPDF(username)} className="btn btn-primary ml-2">Downloads PDF</button>
      </div>
      <div className="table-responsive">
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col">訂單內容</th>
              <th scope="col">總數量</th>
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