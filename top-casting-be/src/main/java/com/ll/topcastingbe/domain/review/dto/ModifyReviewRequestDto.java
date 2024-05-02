package com.ll.topcastingbe.domain.review.dto;

import lombok.Data;

@Data
public class ModifyReviewRequestDto {
    private Long id;
    private String imgUrl;
    private String title;
    private String content;
    private int rating;
}
