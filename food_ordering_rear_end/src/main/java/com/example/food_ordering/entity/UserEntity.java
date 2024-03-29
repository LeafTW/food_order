package com.example.food_ordering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
public class UserEntity {

    /**建值*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**使用者名稱*/
    @Column
    private String name;

    /**帳號*/
    @Column
    private String username;

    /**密碼*/
    @Column
    private String password;

    /**信箱*/
    @Column
    private String email;

    /**電話*/
    @Column
    private Integer phone;

}
