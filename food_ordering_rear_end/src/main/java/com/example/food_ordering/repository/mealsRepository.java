package com.example.food_ordering.repository;

import com.example.food_ordering.entity.cartEntity;
import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface mealsRepository extends JpaRepository<mealsEntity, Long> {

    /**
     * item join meals query
     **/
    @Query("select DISTINCT new com.example.food_ordering.entity.itemEntity(i.id, i.item ,i.item_name) from itemEntity i join mealsEntity m on i.item = m.item ")
    public List<itemEntity> findItemWithCount();

    /**
     * item query
     **/
    public Page<mealsEntity> findAllByItem(Character item, Pageable pageable);

    /**
     * cart add
     **/
    cartEntity save(cartEntity c);

    /**
     * cart query
     **/
    @Query("SELECT c FROM cartEntity c where c.username = ?1")
    List<cartEntity> findCartEntityByUsername(String username);
    /**
     * cart query bu nullUsername
     **/
    @Query("SELECT c FROM cartEntity c where c.username  is Null")
    List<cartEntity> findCartEntityByNullUsername();

    /**
     * cart delete
     **/
    @Modifying
    @Transactional//確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    @Query("delete from cartEntity c where c.id =?1 ")
    void deleteCartEntityById(Long id);

    /**
     * cart update
     **/
    @Modifying
    @Transactional//確保方法在事務中執行。將該註解添加到你的Service方法上，以確保刪除操作發生在事務內部
    @Query("update cartEntity c set c.quantity = ?1 where c.id = ?2")
    Integer updateCartEntityById(Integer quantity, Long id);
}
