package com.example.food_ordering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "total_order")
public class TotalOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer totalprice;

    @Column
    private Integer totalquantity;

    @Column
    private String username;

    @Column
    private Integer orderitem;

    @Column
    private Timestamp date;

    @Column
    private String state;

    public TotalOrderEntity() {
        this.totalprice=0;
        this.totalquantity=0;
    }
}
