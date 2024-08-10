package com.example.food_ordering.mapper;

import com.example.food_ordering.dto.MealsItemDTO;
import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.MealsEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MealsMapper {

//    @Select("SELECT i.* FROM item i JOIN meals m ON i.item = m.item")
    List<ItemEntity> findItemWithCount();

//    @Select("SELECT * FROM meals WHERE item = #{item}")
    List<MealsEntity> findAllByItem(@Param("item") String item);

//    @Insert("INSERT INTO cart (id, username, quantity, item) VALUES (#{id}, #{username}, #{quantity}, #{item})")
    void saveCartEntity(CartEntity c);

//    @Select("SELECT * FROM cart WHERE username = #{username}")
    List<CartEntity> findCartEntityByUsername(@Param("username") String username);

//    @Select("SELECT * FROM cart WHERE username IS NULL")
    List<CartEntity> findCartEntityByNullUsername();

//    @Delete("DELETE FROM cart WHERE id = #{id}")
    void deleteCartEntityById(@Param("id") Long id);

//    @Update("UPDATE cart SET quantity = #{quantity} WHERE id = #{id}")
    void updateCartEntityById(@Param("quantity") Integer quantity, @Param("id") Long id);

    List<MealsEntity> findAllByItemWithPagination(@Param("item") String item, @Param("limit") int limit, @Param("offset") int offset);

    List<MealsItemDTO> findMealsAllWithItem();
}
