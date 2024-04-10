package com.ll.topcastingbe.domain.option.dto.request;

import com.ll.topcastingbe.domain.option.entity.Option;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCreateOptionRequestDto {

    @NotBlank
    private String colorName;
    @PositiveOrZero
    private int stock;

    public static ItemCreateOptionRequestDto toDto(Option option){
        return ItemCreateOptionRequestDto.builder()
                .colorName(option.getColorName())
                .stock(option.getStock())
                .build();
    }
}
