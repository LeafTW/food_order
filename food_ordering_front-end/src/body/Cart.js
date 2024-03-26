
import React, { useState, useEffect } from 'react';
import './css/Cart.css'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


export const Cart = (props) => {

    const [cartData, setCartData] = useState(null);
    //會員使用者名稱
    const { username } = props.userData ?? {};
    //購物車清單html
    const cartDataArray = [];
    //購物車總價格
    let totalPrice = 0;
    //router重新導向
    let navigate=useNavigate();

    //Quantity數量觸發處理
    const handleQuantityChange = (event, product) => {
        const newQuantity = parseInt(event.target.value);
        // 确保新数量在合理的范围内
        if (newQuantity >= 1 && newQuantity <= 99) {
            // 更新产品对象中的数量
            product.quantity = newQuantity;
            //發送更新post
            QuantityUpdate(product.quantity, product.id);
            // 触发 React 的状态更新，以重新渲染组件
            setCartData([...cartData]);
        }
    };

    //Cart刪除get
    const CartDelete = (event, cartId) => {
        // 防止a標籤Route行為
        event.preventDefault();
        let url = `http://localhost:8080/mealsList/deleteCartEntityById/${cartId}`;
        axios.delete(url)
            .then(response => {
                // console.log(response);
                // 在删除完成后重新获取购物车数据，并更新状态
                let cartUrl = `http://localhost:8080/mealsList/cartEntityByUsername/${username}`;
                axios.post(cartUrl)
                    .then(cartResponse => {
                        // console.log(cartResponse.data);
                        setCartData(cartResponse.data);
                    });
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    //Quantity數量更新Post
    const QuantityUpdate = (quantity, cartId) => {
        let url = `http://localhost:8080/mealsList/updateCartEntityById/${quantity}/${cartId}`;
        axios.post(url)
        // .then(response => {
        //     console.log(response);
        // });
    };

    //取得購物車
    useEffect(() => {
        // console.log(username);
        let url = `http://localhost:8080/mealsList/cartEntityByUsername/${username}`;
        axios.post(url)
            .then(response => {
                // console.log(response.data);
                setCartData(response.data);
            });
    }, [username]);

    //新增至訂單
    const AddOrder = (event) => {
        // 防止a標籤Route行為
        event.preventDefault();
        let url = `http://localhost:8080/orderController/orderAdd/${username}`;
        if (username == null) {
            url = `http://localhost:8080/orderController/orderAdd/null`;
        }
        axios.put(url)
            .then((response) => {
                // console.log(response);
                // 等待購物車資料更新完成後再進行跳轉
                // 使用 history.push 跳轉到訂單頁面
                navigate('/order');
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    cartData && cartData.map(data => {
        totalPrice = data.price * data.quantity + totalPrice;
        cartDataArray.push(
            <div className="product" key={data.id}>
                <div className="product-image">
                    <img src="https://s.cdpn.io/3/dingo-dog-bones.jpg" />
                </div>
                <div className="product-details">
                    <div className="product-title">{data.name}</div>
                    {/* <p className="product-description">The best dog bones of all time. Holy crap. Your dog will be begging for these things! I got curious once and ate one myself. I'm a fan.</p> */}
                </div>
                <div className="product-price">{data.price}</div>
                <div className="product-quantity">
                    <input type="number" value={data.quantity} min="1" max="99" onChange={(event) => handleQuantityChange(event, data)} />
                </div>
                <div className="product-removal">
                    <button className="remove-product" onClick={e => CartDelete(e, data.id)}>
                        Remove
                    </button>
                </div>
                <div className="product-line-price">{data.price * data.quantity}</div>
            </div>
        )

    })

    return (

        <div className='cartBody'>
            <h1 className='cartH1'>購物車</h1>

            <div className="shopping-cart">

                <div className="column-labels">
                    <label className="product-image cartLabel">Image</label>
                    <label className="product-details cartLabel">Product</label>
                    <label className="product-price cartLabel">Price</label>
                    <label className="product-quantity cartLabel">Quantity</label>
                    <label className="product-removal cartLabel">Remove</label>
                    <label className="product-line-price cartLabel">Total</label>
                </div>

                {cartDataArray}

                <div className="totals">
                    <div className="totals-item totals-item-total">
                        <label className='cartLabel'>Total</label>
                        <div className="totals-value" id="cart-total">{totalPrice}</div>
                    </div>
                </div>

                <button className="checkout " onClick={(event)=>{AddOrder(event)}}>送出</button>
            </div>
        </div>
    )
}
export default Cart;
