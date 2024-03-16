import React, { Component ,useState } from 'react'

const Card = (props) => {
    let { id, name, price, item } = props.cardData;

    const setcart=(event,name,price)=>{
        // 防止a標籤Route行為
        // event.preventDefault();
        props.cartPostChange(event,"name",name);
        props.cartPostChange(event,"price",price);
    }

    return (

        <div className="col-sm-6 col-md-3" >
            <div className="card mb-3">
                <div className="card-body">
                    <h5 className="card-title">{name}</h5>
                    <p className="card-text">{price}</p>
                    <a href="#" onClick={(event)=>setcart(event,name,price)}className="btn btn-primary">加入購物車</a>
                </div>
            </div>
        </div>

    )
}

export default Card