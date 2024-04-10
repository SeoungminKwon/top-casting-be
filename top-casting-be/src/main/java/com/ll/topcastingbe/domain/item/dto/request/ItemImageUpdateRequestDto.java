package com.ll.topcastingbe.domain.item.dto.request;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ItemImageUpdateRequestDto {
    private String itemImage;
    private String itemDetailedImage;

    public boolean hasImage(){
        return StringUtils.hasText(itemImage);
    }

    public boolean hasDetailedImage(){
        return StringUtils.hasText(itemDetailedImage);
    }
}
