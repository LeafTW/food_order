package com.example.food_ordering.repository;

import com.example.food_ordering.entity.CartEntity;
import com.example.food_ordering.entity.ItemEntity;
import com.example.food_ordering.entity.MealsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MealsRepository extends JpaRepository<MealsEntity, Long> {

    /**
     * item join meals query
     **/
    @Query("select DISTINCT new com.example.food_ordering.entity.ItemEntity(i.id, i.item ,i.item_name) from ItemEntity i join MealsEntity m on i.item = m.item ")
    public List<ItemEntity> findItemWithCount();

    /**
     * item query
     **/
    public Page<MealsEntity> findAllByItem(Character item, Pageable pageable);

    /**
     * cart add
     **/
    CartEntity save(CartEntity c);

    /**
     * cart query
     **/
    @Query("SELECT c FROM CartEntity c where c.username = ?1")
    List<CartEntity> findCartEntityByUsername(String username);
    /**
     * cart query bu nullUsername
     **/
    @Query("SELECT c FROM CartEntity c where c.username  is Null")
    List<CartEntity> findCartEntityByNullUsername();

    /**
     * cart delete
     **/
    @Modifying
    @Transactional//確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    @Query("delete from CartEntity c where c.id =?1 ")
    void deleteCartEntityById(Long id);

    /**
     * cart update
     **/
    @Modifying
    @Transactional//確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    @Query("update CartEntity c set c.quantity = ?1 where c.id = ?2")
    Integer updateCartEntityById(Integer quantity, Long id);
}
