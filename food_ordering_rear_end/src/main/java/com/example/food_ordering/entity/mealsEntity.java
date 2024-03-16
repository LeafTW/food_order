package com.example.food_ordering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "meals")
public class MealsEntity {

    /**建值*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private Character item;
}
