package com.ll.topcastingbe.domain.option.dto.request;

import com.ll.topcastingbe.domain.option.entity.Option;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCreateOptionRequestDto {

    private String colorName;
    private int stock;

    public static ItemCreateOptionRequestDto toDto(Option option){
        return ItemCreateOptionRequestDto.builder()
                .colorName(option.getColorName())
                .stock(option.getStock())
                .build();
    }
}
