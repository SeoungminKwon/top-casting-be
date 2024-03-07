package com.ll.topcastingbe.domain.item.repository;

import com.ll.topcastingbe.domain.item.entity.Item;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //list -> dto -> slice
    @Query("SELECT i FROM Item i " +
            "WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Item> findListByItemNameIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT it FROM Item it JOIN FETCH it.image i")
    List<Item> findAllItems(Pageable pageable);
}