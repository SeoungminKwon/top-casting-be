package com.ll.topcastingbe.domain.review.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.ll.topcastingbe.domain.image.entity.ReviewImage;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private OrderItem orderItem;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review image_id")
    private ReviewImage image;
    private String title;
    private String content;
    private int rating;

    public void modifyContent(String content) {
        this.content = content;
    }

    public void modifyImg(ReviewImage img) {
        this.image = img;
    }

    public void modifyRating(int rating) {
        this.rating = rating;
    }

    public void modifyTitle(String title) {
        this.title = title;
    }
}
