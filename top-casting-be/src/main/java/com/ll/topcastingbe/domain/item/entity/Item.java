package com.ll.topcastingbe.domain.item.entity;

import com.ll.topcastingbe.domain.category.entity.MainCategory;
import com.ll.topcastingbe.domain.category.entity.SubCategory;
import com.ll.topcastingbe.domain.image.entity.DetailedImage;
import com.ll.topcastingbe.domain.image.entity.Image;
import com.ll.topcastingbe.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private BigDecimal itemPrice;

    @OneToOne(fetch = FetchType.LAZY)
    private Image image;
    @OneToOne(fetch = FetchType.LAZY)
    private DetailedImage detailedImage;
    @ManyToOne(fetch = FetchType.LAZY)
    private MainCategory mainCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategory subCategory;

    public void changeItemName(String itemName) {
        this.itemName = itemName;
    }

    public void changeItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
}
