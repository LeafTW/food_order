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
public class ItemEntity {
    /**建值*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer id;

    @Column
    private String item;

    @Column
    private String item_name;

    @Column
    private Boolean show;

    public ItemEntity(Integer id, String item, String item_name, Boolean show) {
        this.id = id;
        this.item = item;
        this.item_name = item_name;
        this.show = show;
    }

    public ItemEntity() {

    }
}
