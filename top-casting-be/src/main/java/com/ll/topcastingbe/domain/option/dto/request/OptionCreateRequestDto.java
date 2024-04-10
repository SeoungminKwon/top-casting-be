package com.ll.topcastingbe.domain.option.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OptionCreateRequestDto {
    @NotNull
    private Long itemId;
    @NotBlank
    private String colorName;
    @PositiveOrZero
    private int stock;
}
