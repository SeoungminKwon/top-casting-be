package com.ll.topcastingbe.domain.item.dto.request;

import com.ll.topcastingbe.domain.option.dto.request.ItemCreateOptionRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCreateRequestDto {

    @NotBlank
    private String itemName;
    @Positive
    @NotNull
    private BigDecimal itemPrice;
    private List<ItemCreateOptionRequestDto> itemColors;
    @NotBlank
    private String itemImage;
    @NotBlank
    private String itemDetailedImage;
    @NotNull
    private Long mainCategoryId;
    @NotNull
    private Long subCategoryId;

}
