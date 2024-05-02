package com.ll.topcastingbe.domain.item.repository;

import com.ll.topcastingbe.domain.item.entity.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //list -> dto -> slice
    @Query("SELECT i FROM Item i " +
                   "WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Item> findListByItemNameIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT it FROM Item it JOIN FETCH it.image i")
    List<Item> findAllItems(Pageable pageable);

    @Query("select it from Item it join fetch it.image i join fetch it.detailedImage di where it.id = :itemId")
    Optional<Item> findByItemIdWithImageAndOption(@Param("itemId") Long itemId);

    @Query("SELECT it FROM Item it JOIN FETCH it.image i WHERE it.mainCategory.id = :mainCategoryId")
    List<Item> findAllItemsByMainCategory(@Param("mainCategoryId") Long mainCategoryId, Pageable pageable);

    @Query("SELECT it FROM Item it JOIN FETCH it.image i WHERE it.subCategory.id = :subCategoryId")
    List<Item> findAllItemsBySubCategory(@Param("subCategoryId") Long subCategoryId,
                                         Pageable pageable);

    Optional<Item> findByItemName(String itemName);
}
