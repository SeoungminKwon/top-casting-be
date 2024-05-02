package com.ll.topcastingbe.domain.item.search.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class SearchItemDto {
    private Long itemId;
    private String itemName;
    private BigDecimal itemPrice;
    private String imageUrl;
    private Long totalOrderedCount;
}
