package com.ll.topcastingbe.domain.item.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItemPriceUpdateRequestDto {
    private BigDecimal itemPrice;
}
