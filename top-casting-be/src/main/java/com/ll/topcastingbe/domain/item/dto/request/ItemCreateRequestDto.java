package com.ll.topcastingbe.domain.item.dto.request;

import com.ll.topcastingbe.domain.option.dto.request.ItemCreateOptionRequestDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCreateRequestDto {

    private String itemName;
    private BigDecimal itemPrice;
    private List<ItemCreateOptionRequestDto> itemColors;
    private String itemImage;
    private String itemDetailedImage;
    private Long mainCategoryId;
    private Long subCategoryId;

}
