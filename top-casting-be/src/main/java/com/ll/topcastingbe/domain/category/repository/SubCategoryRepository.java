package com.ll.topcastingbe.domain.category.repository;

import com.ll.topcastingbe.domain.category.entity.SubCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("select sc from SubCategory sc join sc.parentCategory pc on pc.id = :mainCategoryId where sc.id = :subCategoryId")
    Optional<SubCategory> findByMainCategoryIdAndSubcategoryId(
            @Param("mainCategoryId") Long mainCategoryId, @Param("subCategoryId") Long subCategoryId);
}
