package com.ll.topcastingbe.domain.review.repository;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.orderItem.option.item.id = :itemId and r.rating = :rating")
    List<Review> findByRatingAndItemId(@Param("rating") int rating, @Param("itemId") Long itemId);


    List<Review> findByWriter(Member member);

    @Query("select r from Review r where r.orderItem.option.item.id =:itemId order by r.rating desc ")
    List<Review> findByItemIdOrderByRatingDesc(@Param("itemId") Long itemId);

    @Query("select r from Review r where r.orderItem.option.item.id =:itemId order by r.rating ASC ")
    List<Review> findByItemIdOrderByRatingAsc(@Param("itemId") Long itemId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.orderItem = :orderItem")
    Long countReviewsByOrderItem(@Param("orderItem") OrderItem orderItem);


}
