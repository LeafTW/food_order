import React, { useState, useEffect } from 'react';
import axios from 'axios';

const baseUrl = process.env.REACT_APP_API_BASE_URL;

const Meals = () => {
    const [meals, setMeals] = useState([]);
    const [currentPages, setCurrentPages] = useState({
        "超值全餐": 0,
        "早餐": 0,
        "1+1星級點": 0,
        "麥當勞分享盒": 0,
    });
    const itemsPerPage = 8;

    useEffect(() => {
        // 獲取餐點資料
        axios.get(`${baseUrl}/mealsList/getMealsAllWithItems`).then(response => {
            setMeals(response.data);
        }).catch(error => {
            console.error('Error fetching meals:', error);
        });
    }, []);

    const categories = ["超值全餐", "早餐", "1+1星級點", "麥當勞分享盒"];

    const handlePageChange = (category, direction) => {
        setCurrentPages(prevState => {
            const newPage = prevState[category] + direction;
            return { ...prevState, [category]: newPage };
        });
    };

    return (
        <div>
            <div className="container">
                <div className="row">
                    {categories.map(category => {
                        const filteredMeals = meals.filter(meal => meal.itemEntity.item_name === category);
                        const totalPages = Math.ceil(filteredMeals.length / itemsPerPage);
                        const currentPage = currentPages[category];

                        return (
                            <div className="col-md-3" key={category}>
                                <h4>{category}</h4>
                                <div className='row'>
                                    {filteredMeals
                                        .slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage)
                                        .map(meal => (
                                            <div className="col-12 mb-3" key={meal.id}>
                                                <div className="card">
                                                    <div className="card-body">
                                                        <h5 className="card-title">{meal.name}</h5>
                                                        <p className="card-text">${meal.price}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        ))
                                    }
                                </div>
                                <div className="d-flex justify-content-between mt-2">
                                    <button
                                        className="btn btn-primary"
                                        disabled={currentPage === 0}
                                        onClick={() => handlePageChange(category, -1)}
                                    >
                                        上一頁
                                    </button>
                                    <button
                                        className="btn btn-primary"
                                        disabled={currentPage === totalPages - 1}
                                        onClick={() => handlePageChange(category, 1)}
                                    >
                                        下一頁
                                    </button>
                                </div>
                            </div>
                        );
                    })}
                </div>
            </div>
        </div>
    );
};

export default Meals;
