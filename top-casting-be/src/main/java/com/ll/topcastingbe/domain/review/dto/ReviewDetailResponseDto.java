package com.ll.topcastingbe.domain.review.dto;

import com.ll.topcastingbe.domain.image.entity.ReviewImage;
import com.ll.topcastingbe.domain.review.entity.Review;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Getter;

@Getter
public class ReviewDetailResponseDto {

    private Long id;
    private String writer;
    private String item;
    private String image;
    private String title;
    private String content;
    private int rating;
    private LocalDateTime createdDate;
    private LocalDateTime modifyDate;

    public ReviewDetailResponseDto(Review review) {
        this.id = review.getId();
        this.writer = review.getWriter().getUsername();
        this.item = review.getOrderItem().getItemName();
        this.image = Optional.ofNullable(review.getImage())
                             .map(ReviewImage::getPath)
                             .orElse(null);
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createdDate = review.getCreateDate();
        this.modifyDate = review.getModifyDate();
    }
}
