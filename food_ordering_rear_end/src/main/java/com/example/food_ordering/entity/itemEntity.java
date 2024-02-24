package com.example.food_ordering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "item")
public class itemEntity {
    /**建值*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long id;

    @Column
    private Character item;

    @Column
    private String item_name;

    public itemEntity(Long id, Character item, String item_name) {
        this.id = id;
        this.item = item;
        this.item_name = item_name;
    }

    public itemEntity() {

    }
}
