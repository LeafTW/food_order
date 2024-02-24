import React, { Component } from 'react'

const Card = (props) => {
    let { id, name, price, item } = props.cardData;
    return (

        <div class="col-sm-6 col-md-3">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">{name}</h5>
                    <p class="card-text">{price}</p>
                    <a href="#" class="btn btn-primary">加入購物車</a>
                </div>
            </div>
        </div>

    )
}

export default Card