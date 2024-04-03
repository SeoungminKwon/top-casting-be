package com.ll.topcastingbe.domain.option.dto.request;

import lombok.Data;

@Data
public class OptionCreateRequestDto {
    private Long itemId;
    private String colorName;
    private int stock;
}
