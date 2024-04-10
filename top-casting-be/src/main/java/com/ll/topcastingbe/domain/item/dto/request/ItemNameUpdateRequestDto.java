package com.ll.topcastingbe.domain.item.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemNameUpdateRequestDto {
    @NotBlank
    private String itemName;
}
