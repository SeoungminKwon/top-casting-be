package com.ll.topcastingbe.domain.option.dto;

import com.ll.topcastingbe.domain.option.entity.Option;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDetailOptionResponseDto {
    private Long optionId;
    private String colorName;
    private int stock;

    public static ItemDetailOptionResponseDto toDto(Option option){
        return ItemDetailOptionResponseDto.builder()
                .optionId(option.getId())
                .colorName(option.getColorName())
                .stock(option.getStock())
                .build();
    }
}
