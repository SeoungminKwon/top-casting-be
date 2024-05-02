package com.ll.topcastingbe.domain.item.dto.response;

import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.option.dto.ItemDetailOptionResponseDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDetailResponseDto {

    private Long itemId;
    private String itemName;
    private BigDecimal itemPrice;
    private List<ItemDetailOptionResponseDto> itemColors;
    private String itemImageUrl;
    private String itemDetailedImageUrl;
    private Long mainCategoryId;
    private Long subCategoryId;

    public static ItemDetailResponseDto toDto(Item item, List<ItemDetailOptionResponseDto> optionDtos) {
        return ItemDetailResponseDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .itemColors(optionDtos)
                .itemImageUrl(item.getImage().getPath())
                .itemDetailedImageUrl(item.getDetailedImage().getPath())
                .mainCategoryId(item.getMainCategory().getId())
                .subCategoryId(item.getSubCategory().getId())
                .build();
    }
}
