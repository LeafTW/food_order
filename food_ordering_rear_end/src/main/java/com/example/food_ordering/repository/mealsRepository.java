package com.example.food_ordering.repository;

import com.example.food_ordering.entity.itemEntity;
import com.example.food_ordering.entity.mealsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface mealsRepository extends JpaRepository<mealsEntity,Long> {

//    @Query("SELECT m.item, COUNT(m) AS item_count FROM mealsEntity m GROUP BY m.item")
//    List<Object[]> findItemCountByItem();


    @Query("select DISTINCT new com.example.food_ordering.entity.itemEntity(i.id, i.item ,i.item_name) from itemEntity i join mealsEntity m on i.item = m.item ")
    public List<itemEntity> findItemWithCount();

    public Page<mealsEntity> findAllByItem( Character item ,Pageable pageable);
}
