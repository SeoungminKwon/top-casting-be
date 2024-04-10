package com.ll.topcastingbe.domain.item.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItemPriceUpdateRequestDto {
    @Positive
    @NotNull
    private BigDecimal itemPrice;
}
