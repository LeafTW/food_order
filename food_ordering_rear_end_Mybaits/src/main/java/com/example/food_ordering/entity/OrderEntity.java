package com.example.food_ordering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private Integer quantity;

    @Column
    private  String username;

    @Column(name = "totalprice")
    private Integer totalPrice;

    @Column(name = "orderitem")
    private Integer orderItem;

    public OrderEntity() {
    }

    public OrderEntity(String name, Integer price, Integer quantity, String username, Integer totalPrice, Integer orderItem) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.username = username;
        this.totalPrice = totalPrice;
        this.orderItem = orderItem;
    }

}
