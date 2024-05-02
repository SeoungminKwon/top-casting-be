package com.ll.topcastingbe.domain.review.dto;


import com.ll.topcastingbe.domain.review.entity.Review;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ReviewListResponseDto {
    private List<ReviewDetailResponseDto> reviewList;

    public ReviewListResponseDto(List<Review> reviews) {
        this.reviewList = reviews.stream()
                                  .map(review -> new ReviewDetailResponseDto(review))
                                  .collect(Collectors.toList());
    }
}
