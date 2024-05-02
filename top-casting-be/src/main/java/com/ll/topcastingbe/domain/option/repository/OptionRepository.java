package com.ll.topcastingbe.domain.option.repository;

import com.ll.topcastingbe.domain.option.entity.Option;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select op from Option op where op.item.id = :itemId")
    List<Option> findByItemId(@Param("itemId") Long itemId);
}
