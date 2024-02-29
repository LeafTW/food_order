import React, { useState, useEffect } from 'react';
import './css/Cart.css'
import axios from 'axios';

export const Cart = (props) => {

    const [cartData, setCartData] = useState(null);
    //會員使用者名稱
    const { username } = props.userData ?? {};
    //購物車清單html
    const cartDataArray = [];

    //Quantity數量觸發處理
    const handleQuantityChange = (event, product) => {
        const newQuantity = parseInt(event.target.value);
        // 确保新数量在合理的范围内
        if (newQuantity >= 1 && newQuantity <= 99) {
            // 更新产品对象中的数量
            product.quantity = newQuantity;
            //發送更新post
            QuantityUpdate(product.quantity,product.id);
            // 触发 React 的状态更新，以重新渲染组件
            setCartData([...cartData]);
        }
    };

    //Cart刪除get
    const CartDelete = (event,cartId) => {
         // 防止a標籤Route行為
         event.preventDefault();
        let url = `http://localhost:8080/mealsList/deleteCartEntityById/${cartId}`;
        axios.get(url)
        .then(response => {
            console.log(response);
            // 在删除完成后重新获取购物车数据，并更新状态
            let cartUrl = `http://localhost:8080/mealsList/cartEntityByUsername/${username}`;
            axios.post(cartUrl)
                .then(cartResponse => {
                    console.log(cartResponse.data);
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
                console.log(response.data);
                setCartData(response.data);
            });
    }, []);


    cartData && cartData.map(data => {
        cartDataArray.push(
            <div class="product">
                <div class="product-image">
                    <img src="https://s.cdpn.io/3/dingo-dog-bones.jpg" />
                </div>
                <div class="product-details">
                    <div class="product-title">{data.name}</div>
                    {/* <p class="product-description">The best dog bones of all time. Holy crap. Your dog will be begging for these things! I got curious once and ate one myself. I'm a fan.</p> */}
                </div>
                <div class="product-price">{data.price}</div>
                <div class="product-quantity">
                    <input type="number" value={data.quantity} min="1" max="99"  onChange={(event) => handleQuantityChange(event, data)} />
                </div>
                <div class="product-removal">
                    <button class="remove-product" onClick={e=>CartDelete(e,data.id)}>
                        Remove
                    </button>
                </div>
                <div class="product-line-price">{data.price * data.quantity}</div>
            </div>
        )

    })


    return (

        <div className='cartBody'>
            <h1 className='cartH1'>購物車</h1>

            <div class="shopping-cart">

                <div class="column-labels">
                    <label class="product-image cartLabel">Image</label>
                    <label class="product-details cartLabel">Product</label>
                    <label class="product-price cartLabel">Price</label>
                    <label class="product-quantity cartLabel">Quantity</label>
                    <label class="product-removal cartLabel">Remove</label>
                    <label class="product-line-price cartLabel">Total</label>
                </div>

                {cartDataArray}

                <div class="totals">
                    <div class="totals-item">
                        <label className='cartLabel'>Subtotal</label>
                        <div class="totals-value" id="cart-subtotal">71.97</div>
                    </div>
                    <div class="totals-item">
                        <label className='cartLabel'>Tax (5%)</label>
                        <div class="totals-value" id="cart-tax">3.60</div>
                    </div>
                    <div class="totals-item">
                        <label className='cartLabel'>Shipping</label>
                        <div class="totals-value" id="cart-shipping">15.00</div>
                    </div>
                    <div class="totals-item totals-item-total">
                        <label className='cartLabel'>Grand Total</label>
                        <div class="totals-value" id="cart-total">90.57</div>
                    </div>
                </div>

                <button class="checkout">Checkout</button>

            </div>
        </div>
    )
}
export default Cart;